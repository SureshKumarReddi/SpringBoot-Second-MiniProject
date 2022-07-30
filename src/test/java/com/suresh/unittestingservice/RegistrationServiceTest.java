package com.suresh.unittestingservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.suresh.models.CityEntity;
import com.suresh.models.CountryEntity;
import com.suresh.models.StateEntity;
import com.suresh.models.UserEntity;
import com.suresh.repositories.CityMasterRepository;
import com.suresh.repositories.CountryMasterRepository;
import com.suresh.repositories.StateMasterRepository;
import com.suresh.repositories.UserDetailsRepository;
import com.suresh.service.impl.RegistrationServiceImpl;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

	@MockBean
	UserDetailsRepository userRepository;
	
	@MockBean
	CountryMasterRepository countryRepository;
    @MockBean
	StateMasterRepository stateRepository;
	
	@InjectMocks
	RegistrationServiceImpl service; 
	//we are injecting user repository into RegistrationServiceImpl using @injectMocks 
	
	@Test
	public void checkEmailTestTrue() {
		when(userRepository.findByUserEmail("suresh.y@hcl.com")).thenReturn(new UserEntity());
		boolean uniqueEmail = service.uniqueEmail("suresh.y@hcl.com");
		System.out.println(uniqueEmail);
		assertFalse(uniqueEmail);
	}
	@Test
	public void checkEmailTestFalse() {
		
		UserEntity entity = new UserEntity();
		entity.setUserEmail("suresh.y@hcl.com");
		when(userRepository.findByUserEmail("suresh.y@hcl.com")).thenReturn(entity);
		boolean uniqueEmail = service.uniqueEmail("suresh.y@hcl.com");
		System.out.println(uniqueEmail);
		assertTrue(uniqueEmail);
	}
	@Test
	public void getCountriesTest() {
		CountryEntity ce1 = new CountryEntity();
		ce1.setCountryId(1);
		ce1.setCountryName("India");
		CountryEntity ce2 = new CountryEntity();
		ce2.setCountryId(2);
		ce2.setCountryName("Usa");
		List<CountryEntity> list = new ArrayList<>();
		list.add(ce1);
		list.add(ce2);
		
		when(countryRepository.findAll()).thenReturn(list);
		
		Map<Integer, String> countries = service.getCountries();
		
		System.out.println(countries);
		System.out.println("list contents " +list);
		
	}
	
	@Test
	public void getStatesTest() {
		
		StateEntity se = new StateEntity();
		se.setCountryId(1);
		se.setStateId(1);
		se.setStateName("Ap");
		
		List<StateEntity> list = new ArrayList<>();
		list.add(se);
		when(stateRepository.findByCountryId(2)).thenReturn(list);
		
		Map<Integer, String> states = service.getStates(2);
	}
}
