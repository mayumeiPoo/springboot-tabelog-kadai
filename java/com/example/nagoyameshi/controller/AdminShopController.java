package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.form.ShopEditForm;
import com.example.nagoyameshi.form.ShopRegisterForm;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.service.ShopService;

@Controller
@RequestMapping("/admin/shop")
public class AdminShopController {
	private final ShopRepository shopRepository;
	private final ShopService shopService;

	public AdminShopController(ShopRepository shopRepository, ShopService shopService) {
		this.shopRepository = shopRepository;
		this.shopService = shopService;
	}

	@GetMapping
	public String index(Model model,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {

		Page<Shop> shopPage = shopRepository.findAll(pageable);

		model.addAttribute("shopPage", shopPage);

		return "admin/shop/index";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);

		model.addAttribute("shop", shop);

		return "admin/shop/show";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("shopRegisterForm", new ShopRegisterForm());
		return "admin/shop/register";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute @Validated ShopRegisterForm shopRegisterForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/shop/register";
		}

		shopService.create(shopRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");

		return "redirect:/admin/shop";
	}

	@GetMapping("/{id}/edit")
	public String edit(@PathVariable(name = "id") Integer id, Model model) {
		Shop shop = shopRepository.getReferenceById(id);
		String imageName = shop.getImageName();

		ShopEditForm shopEditForm = new ShopEditForm(shop.getId(), shop.getName(), null, shop.getDescription(),
				shop.getPrice(), shop.getCapacity(), shop.getPostalCode(), shop.getAddress(), shop.getPhoneNumber(),
				shop.getStation(), shop.getTimeStart(), shop.getTimeEnd(), shop.getCategory());

		model.addAttribute("imageName", imageName);
		model.addAttribute("shopEditForm", shopEditForm);

		return "admin/shop/edit";
	}

	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated ShopEditForm shopEditForm, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/shop/edit";
		}

		shopService.update(shopEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");

		return "redirect:/admin/shop";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
		shopRepository.deleteById(id);

		redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");

		return "redirect:/admin/shop";
	}
}