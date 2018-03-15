package guru.springfamework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class VendorControllerTest {

    private static final String BASE_URL = "/api/v1/vendors";
    private static final Long ID = 12L;
    private static final String NAME = "Bobbins & Sons";
    private static final String VENDOR_URL = BASE_URL + "/" + ID;

    @InjectMocks
    private VendorController controller;

    @Mock
    private VendorService service;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void getAllVendors() throws Exception {

        List<VendorDTO> dtos = Arrays.asList(new VendorDTO(), new VendorDTO(), new VendorDTO());

        when(service.getAllVendors()).thenReturn(dtos);

        mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)));
    }

    @Test
    public void getVendorById() throws Exception {

        VendorDTO dto = new VendorDTO(NAME, VENDOR_URL);

        when(service.getVendorById(anyLong())).thenReturn(dto);

        mockMvc.perform(get(VENDOR_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }

    @Test
    public void getVendorByIdNotFound() throws Exception {

        when(service.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(BASE_URL + "/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createNewVendor() throws Exception {

        VendorDTO dto = new VendorDTO(NAME, VENDOR_URL);

        when(service.createNewVendor(any(VendorDTO.class))).thenReturn(dto);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }

    @Test
    public void updateVendor() throws Exception {

        VendorDTO dto = new VendorDTO();
        dto.setName(NAME);

        VendorDTO savedDto = new VendorDTO(NAME, VENDOR_URL);

        when(service.updateVendor(anyLong(), any(VendorDTO.class))).thenReturn(savedDto);

        mockMvc.perform(put(VENDOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }

    @Test
    public void patchVendor() throws Exception {

        VendorDTO dto = new VendorDTO();
        dto.setName(NAME);

        VendorDTO savedDto = new VendorDTO(NAME, VENDOR_URL);

        when(service.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(savedDto);

        mockMvc.perform(patch(VENDOR_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }

    @Test
    public void patchVendorNotFound() throws Exception {

        VendorDTO dto = new VendorDTO();
        dto.setName(NAME);

        when(service.patchVendor(anyLong(), any(VendorDTO.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(patch(BASE_URL + "/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteVendor() throws Exception {

        // Mocking a method that returns void
        doAnswer((Answer<Void>) invocation -> {
            Stream.of(invocation.getArguments()).forEach(arg -> log.debug("Arg = " + arg.toString()));
            return null;
        }).when(service).deleteVendor(anyLong());

        mockMvc.perform(delete(VENDOR_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).deleteVendor(anyLong());
    }

}