package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.dto.CartDetailDto;
import com.example.projectlivingtogether.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("select new com.example.projectlivingtogether.dto.CartDetailDto(ci.id, i.price, ci.count, i.itemName, im.imageUrl) " +
            "from CartItem ci, ItemImage im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.rpImage = 'Y' " +
            "order by ci.createdDate desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
