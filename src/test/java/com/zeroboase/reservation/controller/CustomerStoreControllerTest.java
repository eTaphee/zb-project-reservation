package com.zeroboase.reservation.controller;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zeroboase.reservation.configuration.security.TokenProvider;
import com.zeroboase.reservation.domain.store.controller.CustomerStoreController;
import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.CustomerStoreInfoDto;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.domain.store.service.CustomerStoreService;
import com.zeroboase.reservation.util.MockMvcUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CustomerStoreController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerStoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerStoreService customerStoreService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("매장 상세 조회 성공")
    void successGetStoreById() throws Exception {
        // given
        given(customerStoreService.getStoreInfo(anyLong()))
            .willReturn(CustomerStoreInfoDto.builder()
                .id(1L)
                .name("store")
                .description("description")
                .tel("02-1234-5678")
                .address("address")
                .starRating(0.0)
                .reviewCount(1L)
                .build());

        // when
        ResultActions resultActions = MockMvcUtil.performGet(mockMvc, "/customer/stores/1");

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("store"))
            .andExpect(jsonPath("$.description").value("description"))
            .andExpect(jsonPath("$.address").value("address"))
            .andExpect(jsonPath("$.tel").value("02-1234-5678"))
            .andExpect(jsonPath("$.starRating").value(0.0))
            .andExpect(jsonPath("$.reviewCount").value(1L));
    }

    @Test
    @DisplayName("매장 상세 조회 실패 - 매장 없음")
    void failGetStoreById_STORE_NOT_FOUND() throws Exception {
        // given
        given(customerStoreService.getStoreInfo(anyLong()))
            .willThrow(new ReservationException(STORE_NOT_FOUND));

        // when
        ResultActions resultActions = MockMvcUtil.performGet(mockMvc, "/customer/stores/1");

        // then
        resultActions
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.errorCode").value(STORE_NOT_FOUND.toString()));
    }

    @Test
    @DisplayName("매장 목록 조회 성공")
    void successGetStoreList() throws Exception {
        // given
        given(customerStoreService.getStoreList(any(), any(), any()))
            .willReturn(PageResponse.<CustomerStoreDto>builder()
                .pageNumber(1)
                .pageSize(10)
                .first(true)
                .last(false)
                .build());

        // when
        ResultActions resultActions = MockMvcUtil.performGet(mockMvc, "/customer/stores");

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageNumber").value(1))
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.last").value(false));
    }
}