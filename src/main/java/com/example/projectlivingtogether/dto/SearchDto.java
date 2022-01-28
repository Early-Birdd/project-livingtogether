package com.example.projectlivingtogether.dto;

import com.example.projectlivingtogether.enumclass.ItemStatus;
import lombok.Data;

@Data
public class SearchDto {

    private ItemStatus searchItemStatus;

    private String searchBy;
    private String searchDate;
    private String searchQuery = "";
}
