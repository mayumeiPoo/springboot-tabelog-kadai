package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Favorite;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.FavoriteRepository;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.FavoriteService;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/shop")
public class ShopController {
	private final ShopRepository shopRepository;
	private final ReviewService reviewService;
	private final ReviewRepository reviewRepository;
	private final FavoriteService favoriteService;
	private final FavoriteRepository favoriteRepository;
	private final CategoryRepository categoryRepository;
	
	public ShopController(ShopRepository shopRepository, ReviewService reviewService, ReviewRepository reviewRepository,FavoriteRepository favoriteRepository, FavoriteService favoriteService,CategoryRepository categoryRepository) {
		this.shopRepository = shopRepository;
		this.reviewService = reviewService;
		this.reviewRepository = reviewRepository;
		this.favoriteRepository = favoriteRepository;
		this.favoriteService = favoriteService;
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping
	
	public String index(@RequestParam(name = "price" , required = false) Integer price,
			            @RequestParam(name = "area" , required = false) String area,
			            @RequestParam(name = "category_id" , required = false) Integer category_id,
			            @RequestParam(name = "capacity" , required = false) Integer capacity,
			            @RequestParam(name = "order", required = false) String order,
			            @PageableDefault(page = 0, size = 8, sort = "id", direction = Direction.ASC) Pageable pageable,
                        Model model)
	{
		Page<Shop>shopPage;
		List<Category> category = categoryRepository.findAll();
		if (price != null) {
			 if (order != null && order.equals("priceAsc")) {
				 shopPage = shopRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
             } else if (order != null && order.equals("priceDesc")){
            	 shopPage = shopRepository.findByPriceLessThanEqualOrderByPriceDesc(price, pageable);
             } else {
            	 shopPage = shopRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
             }
        } else if (area != null && !area.isEmpty()) {
        	if (order != null && order.equals("priceAsc")) {
            shopPage = shopRepository.findByStationLikeOrderByPriceAsc("%" + area + "%", pageable);
        	} else if (order != null && order.equals("priceDesc")){
        		shopPage = shopRepository.findByStationLikeOrderByPriceDesc("%" + area + "%", pageable);
        	} else {
        		shopPage = shopRepository.findByStationLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
        	}
        } else if (category_id != null) {
        	if (order != null && order.equals("priceAsc")) {
            shopPage = shopRepository.findByCategoryIdOrderByPriceAsc(category_id , pageable);
        	} else if (order != null && order.equals("priceDesc")){
        		shopPage = shopRepository.findByCategoryIdOrderByPriceDesc(category_id , pageable);
        	} else {
        		shopPage = shopRepository.findByCategoryIdOrderByCreatedAtDesc(category_id , pageable);
        	}
        } else if(capacity != null) {
        	if (order != null && order.equals("priceAsc")) {
        		shopPage = shopRepository.findByCapacityGreaterThanEqualOrderByPriceAsc(capacity, pageable);
        	} else if (order != null && order.equals("priceDesc")){
        		shopPage = shopRepository.findByCapacityGreaterThanEqualOrderByPriceDesc(capacity, pageable);
        	} else {
            shopPage = shopRepository.findByCapacityGreaterThanEqualOrderByCreatedAtDesc(capacity, pageable);
        	}
        } else {
        	if (order != null && order.equals("priceAsc")) {
        		shopPage = shopRepository.findAllByOrderByPriceAsc(pageable);
        	} else if (order != null && order.equals("priceDesc")){
        		shopPage = shopRepository.findAllByOrderByPriceDesc(pageable);
        	} else {
            shopPage = shopRepository.findAllByOrderByCreatedAtDesc(pageable);
        } 
        	
        }
		
		model.addAttribute("shopPage",shopPage);
		model.addAttribute("price",price);
		model.addAttribute("area",area);
		model.addAttribute("caregory_id",category_id);
		model.addAttribute("capacity",capacity);
		model.addAttribute("order", order);
		model.addAttribute("category", category);
		
		return "shop/index";
	
	
}
	
	@GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model,@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        Shop shop = shopRepository.getReferenceById(id);
        
        Favorite favorite = null;
        boolean isFavorite = false;
        if (userDetailsImpl != null) {
        	User user = userDetailsImpl.getUser();
        	isFavorite = favoriteService.isFavorite(shop, user);
        	if (isFavorite) {
        		favorite = favoriteRepository.findByShopAndUser(shop, user);
        	}
        	
        }
        
        
        model.addAttribute("shop", shop);
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        model.addAttribute("favorite", favorite);
        model.addAttribute("isFavorite", isFavorite);
       
        
        
        return "shop/show";
	
	}
	
	
	
	
	
}
