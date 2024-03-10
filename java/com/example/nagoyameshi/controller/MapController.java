package com.example.nagoyameshi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.ShopRepository;

@Controller
@RequestMapping("shop/{shopId}/map")
public class MapController {
	private final ShopRepository shopRepository; 
	
    public MapController(ShopRepository shopRepository) {
    	this.shopRepository=shopRepository;
    }
	@GetMapping
	public String index(@PathVariable(name = "shopId") Integer shopId, Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		model.addAttribute("shop", shop);
        return "map/index";
    }

}
