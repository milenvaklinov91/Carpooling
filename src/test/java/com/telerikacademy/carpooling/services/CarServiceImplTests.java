package com.telerikacademy.carpooling.services;

import com.telerikacademy.carpooling.exceptions.UnauthorizedOperationException;
import com.telerikacademy.carpooling.models.Car;
import com.telerikacademy.carpooling.models.User;
import com.telerikacademy.carpooling.repositories.interfaces.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTests {

    @Mock
    CarRepository carRepository;
    @InjectMocks
    CarServiceImpl carService;

    @Test
    public void getCarById_Should_ReturnCar_When_MatchExist() {
        Car mockCar = Helper.createMockCar();
        when(carRepository.getCarById(Mockito.anyInt()))
                .thenReturn(mockCar);

        Car result = carService.getCarById(mockCar.getCarId());

        assertEquals(mockCar, result);
    }

    @Test
    public void create_Should_CreateCar_When_CarIsValid() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setIsDriver(true);

        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        assertDoesNotThrow(() -> carService.create(mockCar, mockUser));
        verify(carRepository, times(1)).create(mockCar);
    }

    @Test
    public void create_Should_ThrowUnauthorizedOperationException_When_UserIsBlocked() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();

        assertThrows(UnauthorizedOperationException.class, () -> carService.create(mockCar, mockUser));
        verify(carRepository, never()).create(mockCar);
    }

    @Test
    public void create_Should_ThrowUnauthorizedOperationException_When_UserIsNotDriver() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setIsDriver(false);

        Car mockCar = Helper.createMockCar();

        assertThrows(UnauthorizedOperationException.class, () -> carService.create(mockCar, mockUser));
        verify(carRepository, never()).create(mockCar);
    }

    @Test
    public void update_Should_UpdateCar_When_CarIsValid() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);

        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        assertDoesNotThrow(() -> carService.update(mockCar, mockUser));
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void update_Should_ThrowUnauthorizedOperationException_When_UserIsBlocked() {
        User mockUser = Helper.createMockUser();

        Car mockCar = Helper.createMockCar();

        assertThrows(UnauthorizedOperationException.class, () -> carService.update(mockCar, mockUser));
        verify(carRepository, never()).update(mockCar);
    }

    @Test
    public void update_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);

        User secondMockUser = Helper.createSecondMockUser();

        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(secondMockUser);

        assertThrows(UnauthorizedOperationException.class, () -> carService.update(mockCar, mockUser));
        verify(carRepository, never()).update(mockCar);
    }

    @Test
    public void delete_Should_Delete_When_UserIsAdmin() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(true);

        Car mockCar = Helper.createMockCar();

        assertDoesNotThrow(() -> carService.delete(mockCar.getCarId(), mockUser));
        verify(carRepository, times(1)).delete(mockCar.getCarId());
    }

    @Test
    public void delete_Should_Delete_When_UserIsCarCreator() {
        User mockUser = Helper.createMockUser();
        mockUser.setIsBlocked(false);
        mockUser.setAdmin(false);

        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        assertDoesNotThrow(() -> carService.delete(mockCar.getCarId(), mockUser));
        verify(carRepository, times(1)).delete(mockCar.getCarId());
    }

    @Test
    public void delete_Should_ThrowUnauthorizedOperationException_When_UserIsBlocked() {
        User mockUser = Helper.createMockUser();

        Car mockCar = Helper.createMockCar();

        assertThrows(UnauthorizedOperationException.class, () -> carService.delete(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).delete(anyInt());
    }

    @Test
    public void delete_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        mockUser.setAdmin(false);

        User secondMockUser = Helper.createSecondMockUser();

        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(secondMockUser);

        assertThrows(UnauthorizedOperationException.class, () -> carService.delete(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).delete(anyInt());
    }

    @Test
    public void extraStorage_Should_SetExtraStorage_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.extraStorage(mockCar.getCarId(), mockUser);

        assertTrue(resultCar.isExtraStorage());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void extraStorage_Should_TrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.extraStorage(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void notExtraStorage_Should_NotSetExtraStorage_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.notExtraStorage(mockCar.getCarId(), mockUser);

        assertFalse(resultCar.isExtraStorage());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void notExtraStorage_Should_Trow_UnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.notExtraStorage(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void Smoke_Should_SetSmokeToTrue_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.smoke(mockCar.getCarId(), mockUser);

        assertTrue(resultCar.isSmoke());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void Smoke_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.smoke(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }


    @Test
    public void notSmoke_Should_SetSmokeToFalse_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.notSmoke(mockCar.getCarId(), mockUser);

        assertFalse(resultCar.isSmoke());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void notSmoke_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.notSmoke(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void AirConditioner_Should_SetAirConditionerToTrue_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.airConditioner(mockCar.getCarId(), mockUser);

        assertTrue(resultCar.isAirConditioner());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void AirConditioner_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.airConditioner(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void notAirConditioner_Should_SetAirConditionerToFalse_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.notAirConditioner(mockCar.getCarId(), mockUser);

        assertFalse(resultCar.isAirConditioner());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void notAirConditioner_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.notAirConditioner(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void PetAvailable_Should_SetPetAvailableToTrue_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.petAvailable(mockCar.getCarId(), mockUser);

        assertTrue(resultCar.isPetAvailable());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void PetAvailable_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.petAvailable(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }


    @Test
    public void notPetAvailable_Should_SetPetAvailableToFalse_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.notPetAvailable(mockCar.getCarId(), mockUser);

        assertFalse(resultCar.isPetAvailable());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void notPetAvailable_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.notPetAvailable(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void ConsumeFood_Should_SetConsumeFoodToTrue_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.consumeFood(mockCar.getCarId(), mockUser);

        assertTrue(resultCar.isConsumeFood());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void ConsumeFood_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.consumeFood(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }


    @Test
    public void notConsumeFood_Should_SetConsumeFoodToFalse_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.notConsumeFood(mockCar.getCarId(), mockUser);

        assertFalse(resultCar.isConsumeFood());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void notConsumeFood_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.notConsumeFood(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }

    @Test
    public void ConsumeDrink_Should_SetConsumeDrinkToTrue_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.consumeDrink(mockCar.getCarId(), mockUser);

        assertTrue(resultCar.isConsumeDrink());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void ConsumeDrink_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.consumeDrink(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }


    @Test
    public void notConsumeDrink_Should_SetConsumeDrinkToFalse_When_CarAndUserIsValid() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(mockUser);

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        Car resultCar = carService.notConsumeDrink(mockCar.getCarId(), mockUser);

        assertFalse(resultCar.isConsumeDrink());
        verify(carRepository, times(1)).update(mockCar);
    }

    @Test
    public void notConsumeDrink_Should_ThrowUnauthorizedOperationException_When_UserIsNotAuthorized() {
        User mockUser = Helper.createMockUser();
        Car mockCar = Helper.createMockCar();
        mockCar.setUserCreatedBy(Helper.createSecondMockUser());

        when(carRepository.getCarById(mockCar.getCarId())).thenReturn(mockCar);

        assertThrows(UnauthorizedOperationException.class, () -> carService.notConsumeDrink(mockCar.getCarId(), mockUser));
        verify(carRepository, never()).update(any());
    }


}
