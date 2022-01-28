package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.dto.MainItemDto;
import com.example.projectlivingtogether.dto.QMainItemDto;
import com.example.projectlivingtogether.dto.SearchDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.QItem;
import com.example.projectlivingtogether.entity.QItemImage;
import com.example.projectlivingtogether.enumclass.ItemStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;

    public ItemRepositoryCustomImpl(EntityManager entityManager){

        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    private BooleanExpression searchItemStatusEq(ItemStatus searchItemStatus){

        return searchItemStatus == null ? null : QItem.item.itemStatus.eq(searchItemStatus);
    }

    private BooleanExpression registerDateAfter(String searchDate){

        LocalDateTime dateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDate) || searchDate == null){

            return null;
        } else if(StringUtils.equals("1d", searchDate)){

            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDate)){

            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDate)){

            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDate)){

            dateTime = dateTime.minusMonths(6);
        } else if(StringUtils.equals("1y", searchDate)){

            dateTime = dateTime.minusYears(1);
        }

        return QItem.item.createdDate.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemName", searchBy)){

            return QItem.item.itemName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createBy", searchBy)){

            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getItemPage(SearchDto searchDto, Pageable pageable) {

        QueryResults<Item> results = jpaQueryFactory
                .selectFrom(QItem.item)
                .where(registerDateAfter(searchDto.getSearchDate()),
                        searchItemStatusEq(searchDto.getSearchItemStatus()),
                        searchByLike(searchDto.getSearchBy(), searchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression itemNameLike(String searchQuery){

        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getMainItemPage(SearchDto searchDto, Pageable pageable) {

        QItem item = QItem.item;
        QItemImage itemImage = QItemImage.itemImage;

        QueryResults<MainItemDto> results = jpaQueryFactory
                .select(new QMainItemDto(
                        item.id,
                        item.price,
                        item.itemName,
                        item.itemDetail,
                        itemImage.imageUrl
                        )
                )
                .from(itemImage)
                .join(itemImage.item, item)
                .where(itemImage.rpImage.eq("Y"))
                .where(itemNameLike(searchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
