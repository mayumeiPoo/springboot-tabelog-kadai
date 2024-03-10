package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop,Integer>{
	
	public Page<Shop> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
	public Page<Shop> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable);
	public Page<Shop> findByPriceLessThanEqualOrderByPriceDesc(Integer price, Pageable pageable);
	
	public Page<Shop> findByStationLikeOrderByCreatedAtDesc(String area, Pageable pageable);
	public Page<Shop> findByStationLikeOrderByPriceAsc(String area, Pageable pageable);
	public Page<Shop> findByStationLikeOrderByPriceDesc(String area, Pageable pageable);
	
	public Page<Shop> findByCategoryIdOrderByCreatedAtDesc(Integer category_id, Pageable pageable);
	public Page<Shop> findByCategoryIdOrderByPriceAsc(Integer category_id, Pageable pageable);
	public Page<Shop> findByCategoryIdOrderByPriceDesc(Integer category_id, Pageable pageable);
	
    public Page<Shop> findByCapacityGreaterThanEqualOrderByCreatedAtDesc(Integer capacity, Pageable pageable);
    public Page<Shop> findByCapacityGreaterThanEqualOrderByPriceAsc(Integer capacity, Pageable pageable);
    public Page<Shop> findByCapacityGreaterThanEqualOrderByPriceDesc(Integer capacity, Pageable pageable);
    
    public Page<Shop> findByPriceLessThanEqualAndStationLikeOrderByPriceAsc(Integer price,String area, Pageable pageable);
    public Page<Shop> findByPriceLessThanEqualAndStationLikeOrderByPriceDesc(Integer price,String area, Pageable pageable);
    public Page<Shop> findByPriceLessThanEqualAndStationLikeOrderByCreatedAtDesc(Integer price,String area, Pageable pageable);
    
    public Page<Shop> findByPriceLessThanEqualAndStationLikeAndCategoryIdOrderByPriceAsc(Integer price,String area, Integer category_id, Pageable pageable);
    public Page<Shop> findByPriceLessThanEqualAndStationLikeAndCategoryIdOrderByPriceDesc(Integer price,String area, Integer category_id, Pageable pageable);
    public Page<Shop> findByPriceLessThanEqualAndStationLikeAndCategoryIdOrderByCreatedAtDesc(Integer price,String area, Integer category_id, Pageable pageable);
    
    public Page<Shop> findAllByOrderByCreatedAtDesc(Pageable pageable);
    public Page<Shop> findAllByOrderByPriceAsc(Pageable pageable);
    public Page<Shop> findAllByOrderByPriceDesc(Pageable pageable);
	public Page<Shop> findByPriceLessThanEqualOrderByCreatedAtAsc(Integer price, Pageable pageable);
	public Page<Shop> findByStationLikeOrderByCreatedAtAsc(String string, Pageable pageable);
	public Page<Shop> findByCategoryIdOrderByCreatedAtAsc(Integer category_id, Pageable pageable);
	public Page<Shop> findByCapacityGreaterThanEqualOrderByCreatedAtAsc(Integer capacity, Pageable pageable);
	public Page<Shop> findAllByOrderByCreatedAtAsc(Pageable pageable);
    
	public Shop findByCategory(Category category);
	public Page<Shop> findByCategoryId(Integer categoryId, Pageable pageable);
}
