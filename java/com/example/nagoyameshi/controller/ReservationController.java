package com.example.nagoyameshi.controller;

 import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationInputForm;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReservationService;
 
 @Controller
public class ReservationController {
     private final ReservationRepository reservationRepository;
     private final ShopRepository shopRepository;
     private final ReservationService reservationService;
     
     
     public ReservationController(ReservationRepository reservationRepository,ShopRepository shopRepository,ReservationService reservationService) {        
         this.reservationRepository = reservationRepository;
         this.shopRepository = shopRepository;
         this.reservationService = reservationService;
     }    
 
     @GetMapping("/reservation")
     public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestParam(name = "tab", defaultValue = "01") String tab,@PageableDefault(page = 0, size = 10, sort = "reservationDate", direction = Direction.ASC) Pageable pageable, Model model) {
         User user = userDetailsImpl.getUser();
         Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
      // 現在の予約と過去の予約を取得
         Page<Reservation> currentReservationsPage = reservationService.getCurrentReservations(user, pageable);
         Page<Reservation> pastReservationsPage = reservationService.getPastReservations(user, pageable);

         // モデルに追加
         model.addAttribute("currentReservationsPage", currentReservationsPage);
         model.addAttribute("pastReservationsPage", pastReservationsPage);
         model.addAttribute("activeTab", tab);

         model.addAttribute("reservationPage", reservationPage);         
         
         return "reservation/index";
     }
     
     
     @GetMapping("/shop/{id}/reservation/createform")
     public String inputform(@PathVariable(name = "id") Integer id, Model model) {
         Shop shop = shopRepository.getReferenceById(id);
         model.addAttribute("shop", shop);  
         model.addAttribute("reservationInputForm", new ReservationInputForm());
         
         return "reservation/createform";
     
     }
 	
     @GetMapping("/shop/{id}/reservation/input")
     public String input(@PathVariable(name = "id") Integer id,
                         @ModelAttribute @Validated ReservationInputForm reservationInputForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model)
     {   
         Shop shop = shopRepository.getReferenceById(id);
         Integer numberOfPeople = reservationInputForm.getNumberOfPeople();   
         Integer capacity = shop.getCapacity();
         String fromReservationTime = reservationInputForm.getFromReservationTime();
         String timeStart = shop.getTimeStart();
         String timeEnd = shop.getTimeEnd();
         
         
         if (numberOfPeople != null) {
             if (!reservationService.isWithinCapacity(numberOfPeople, capacity)) {
                 FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "予約人数が定員を超えています。");
                 bindingResult.addError(fieldError);                
             }            
         }   
         if (fromReservationTime != null) {
             if (!reservationService.isWithinTimeStart(fromReservationTime,timeStart)) {
                 FieldError fieldError = new FieldError(bindingResult.getObjectName(), "fromReservationTime", "予約時間は営業時間内でお願いします。");
                 bindingResult.addError(fieldError);                
             }            
         } 
         if (fromReservationTime != null) {
             if (!reservationService.isWithinTimeEnd(fromReservationTime,timeEnd)) {
                 FieldError fieldError = new FieldError(bindingResult.getObjectName(), "fromReservationTime", "予約時間は営業時間内でお願いします。");
                 bindingResult.addError(fieldError);                
             }            
         } 
         
         
         if (bindingResult.hasErrors()) {            
             model.addAttribute("shop",shop );            
             model.addAttribute("errorMessage", "予約内容に不備があります。"); 
             return "reservation/createform";
         }
         
         
         redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);           
         
         return "redirect:/shop/{id}/reservation/confirm";
     }    
     
     @GetMapping("/shop/{id}/reservation/confirm")
     public String confirm(@PathVariable(name = "id") Integer id,
                           @ModelAttribute ReservationInputForm reservationInputForm,
                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,                          
                           Model model) 
     {        
        Shop shop = shopRepository.getReferenceById(id);
         User user = userDetailsImpl.getUser(); 
                 
        
         LocalDate reservationDate = reservationInputForm.getReservationDate();
         LocalTime reservationTime = reservationInputForm.getReservationTime();
        
  
         
         
         ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(shop.getId(), user.getId(), reservationDate.toString(), reservationTime.toString(), reservationInputForm.getNumberOfPeople());

         
         model.addAttribute("shop", shop);  
         model.addAttribute("reservationRegisterForm", reservationRegisterForm);       
         
         return "reservation/confirm";
     }
     @PostMapping("/shop/{id}/reservation/create")
     public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {                
         reservationService.create(reservationRegisterForm);        
         
         return "redirect:/reservation?reserved";
     }
     
     @PostMapping("/reservation/{id}/delete")
     public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
         reservationRepository.deleteById(id);
                 
         redirectAttributes.addFlashAttribute("successMessage", "予約をキャンセルしました。");
         
         return "redirect:/reservation";
     }

}