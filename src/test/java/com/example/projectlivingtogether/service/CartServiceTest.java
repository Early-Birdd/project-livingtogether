package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.CartItemDto;
import com.example.projectlivingtogether.entity.CartItem;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.Member;
import com.example.projectlivingtogether.enumclass.ItemStatus;
import com.example.projectlivingtogether.repository.CartItemRepository;
import com.example.projectlivingtogether.repository.ItemRepository;
import com.example.projectlivingtogether.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class CartServiceTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    public Item saveItem(){

        Item item = new Item();
        item.setItemStatus(ItemStatus.SALE);
        item.setPrice(50000);
        item.setItemQuantity(50);
        item.setItemName("테스트 상품");
        item.setItemDetail("테스트 상품 상세");

        return itemRepository.save(item);
    }

    public Member saveMember(){

        Member member = new Member();
        member.setEmail("test@test.mail");

        return memberRepository.save(member);
    }

    @Test
    @DisplayName("장바구니 담기")
    public void addCart(){

        Item item = saveItem();

        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setItemId(item.getId());
        cartItemDto.setCount(5);

        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(item.getId()).isEqualTo(cartItem.getItem().getId());
        Assertions.assertThat(cartItemDto.getCount()).isEqualTo(cartItem.getCount());
    }
}
