package com.msbook.dto;

import java.util.List;

public record FiltersBookDtoRequest(String title, Long authorId, List<Long> categoriesId, Float rating) {

}
