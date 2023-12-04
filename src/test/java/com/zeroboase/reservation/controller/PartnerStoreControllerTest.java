package com.zeroboase.reservation.controller;

import static com.zeroboase.reservation.exception.ErrorCode.STORE_NOT_FOUND;
import static com.zeroboase.reservation.exception.ErrorCode.VALIDATION_FAIL;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.INVALID_TEL_FORMAT;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.STORE_ADDRESS_NOT_BLANK;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.STORE_DESCRIPTION_NOT_BLANK;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.STORE_LATITUDE_NOT_NULL;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.STORE_LONGITUDE_NOT_NULL;
import static com.zeroboase.reservation.validator.constant.ValidationMessage.STORE_NAME_NOT_BLANK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zeroboase.reservation.configuration.security.TokenProvider;
import com.zeroboase.reservation.domain.common.dto.PageResponse;
import com.zeroboase.reservation.domain.store.controller.PartnerStoreController;
import com.zeroboase.reservation.domain.store.dto.CreateStore;
import com.zeroboase.reservation.domain.store.dto.UpdateStore;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreDto;
import com.zeroboase.reservation.domain.store.dto.model.PartnerStoreInfoDto;
import com.zeroboase.reservation.domain.store.service.PartnerStoreService;
import com.zeroboase.reservation.exception.ReservationException;
import com.zeroboase.reservation.util.MockMvcUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(PartnerStoreController.class)
@AutoConfigureMockMvc(addFilters = false)
class PartnerStoreControllerTest {

    private final static String CREATE_STORE_URL = "/partner/stores";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartnerStoreService partnerStoreService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("매장 등록 성공")
    void successCreateStore() throws Exception {
        // given

        given(partnerStoreService.createStore(any())).willReturn(PartnerStoreInfoDto.builder()
            .name("store")
            .starRating(0.0)
            .reviewCount(0L)
            .build());

        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .name("store")
            .description("description")
            .tel("02-1234-5678")
            .address("address")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("매장 등록 실패 - 유효성 검사(name @NotBlank")
    void failCreateStore_name_NotBlank() throws Exception {
        // given
        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .description("description")
            .tel("02-1234-5678")
            .address("address")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(STORE_NAME_NOT_BLANK));
    }

    @Test
    @DisplayName("매장 등록 실패 - 유효성 검사(description @NotBlank")
    void failCreateStore_description_NotBlank() throws Exception {
        // given
        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .name("store")
            .tel("02-1234-5678")
            .address("address")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(STORE_DESCRIPTION_NOT_BLANK));
    }

    @Test
    @DisplayName("매장 등록 실패 - 유효성 검사(tel @Pattern")
    void failCreateStore_tel_Pattern() throws Exception {
        // given
        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .name("store")
            .description("description")
            .tel("02-12345-634")
            .address("address")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(INVALID_TEL_FORMAT));
    }

    @Test
    @DisplayName("매장 등록 실패 - 유효성 검사(address @NotBlank")
    void failCreateStore_address_NotBlank() throws Exception {
        // given
        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .name("store")
            .description("description")
            .tel("02-1234-5678")
            .latitude(37.0)
            .longitude(127.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(STORE_ADDRESS_NOT_BLANK));
    }

    @Test
    @DisplayName("매장 등록 실패 - 유효성 검사(latitude @NotNull")
    void failCreateStore_latitude_NotNull() throws Exception {
        // given
        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .name("store")
            .description("description")
            .tel("02-1234-5678")
            .address("address")
            .longitude(127.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(STORE_LATITUDE_NOT_NULL));
    }

    @Test
    @DisplayName("매장 등록 실패 - 유효성 검사(longitude @NotNull")
    void failCreateStore_longitude_NotNull() throws Exception {
        // given
        // when
        CreateStore.Request request = CreateStore.Request.builder()
            .name("store")
            .description("description")
            .tel("02-1234-5678")
            .address("address")
            .latitude(37.0)
            .build();

        ResultActions resultActions = MockMvcUtil.performPost(mockMvc, CREATE_STORE_URL,
            request);

        // then
        resultActions
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.errorCode").value(VALIDATION_FAIL.toString()))
            .andExpect(jsonPath("$.description").value(STORE_LONGITUDE_NOT_NULL));
    }

    @Test
    @DisplayName("매장 상세 조회 성공")
    void successGetStoreById() throws Exception {
        // given
        given(partnerStoreService.getStoreInfo(anyLong()))
            .willReturn(PartnerStoreInfoDto.builder()
                .id(1L)
                .name("store")
                .description("description")
                .tel("02-1234-5678")
                .address("address")
                .starRating(0.0)
                .reviewCount(1L)
                .build());

        // when
        ResultActions resultActions = MockMvcUtil.performGet(mockMvc, "/partner/stores/1");

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
        given(partnerStoreService.getStoreInfo(anyLong()))
            .willThrow(new ReservationException(STORE_NOT_FOUND));

        // when
        ResultActions resultActions = MockMvcUtil.performGet(mockMvc, "/partner/stores/1");

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
        given(partnerStoreService.getStoreList(any()))
            .willReturn(PageResponse.<PartnerStoreDto>builder()
                .pageNumber(1)
                .pageSize(10)
                .first(true)
                .last(false)
                .build());

        // when
        ResultActions resultActions = MockMvcUtil.performGet(mockMvc, "/partner/stores");

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageNumber").value(1))
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.first").value(true))
            .andExpect(jsonPath("$.last").value(false));
    }

    @Test
    @DisplayName("매장 수정 성공")
    void successUpdateStore() throws Exception {
        // given
        given(partnerStoreService.updateStore(anyLong(), any())).willReturn(
            UpdateStore.Response.builder()
                .name("store")
                .description("description")
                .tel("02-1234-5678")
                .address("address")
                .latitude(37.0)
                .longitude(127.0)
                .build());

        // when
        UpdateStore.Request request = UpdateStore.Request.builder()
            .tel("02-1234-5678")
            .build();

        ResultActions resultActions = MockMvcUtil.performPatch(mockMvc, "/partner/stores/1",
            request);

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("store"))
            .andExpect(jsonPath("$.description").value("description"))
            .andExpect(jsonPath("$.address").value("address"))
            .andExpect(jsonPath("$.tel").value("02-1234-5678"))
            .andExpect(jsonPath("$.latitude").value(37.0))
            .andExpect(jsonPath("$.longitude").value(127));
    }

    @Test
    @DisplayName("매장 수정 실패 - 매장 없음")
    void failUpdateStore_STORE_NOT_FOUND() throws Exception {
        // given
        given(partnerStoreService.updateStore(anyLong(), any()))
            .willThrow(new ReservationException(STORE_NOT_FOUND));

        // when
        UpdateStore.Request request = UpdateStore.Request.builder()
            .tel("02-1234-5678")
            .build();

        ResultActions resultActions = MockMvcUtil.performPatch(mockMvc, "/partner/stores/1",
            request);

        // then
        resultActions.andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.errorCode").value(STORE_NOT_FOUND.toString()));
    }

    @Test
    @DisplayName("매장 삭제 성공")
    void successDeleteStore() throws Exception {
        // given
        // when
        ResultActions resultActions = MockMvcUtil.performDelete(mockMvc, "/partner/stores/1", null);

        // then
        resultActions.andExpect(status().isNoContent());
    }
}