package com.spiralforge.hothoagies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spiralforge.hothoagies.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

}
