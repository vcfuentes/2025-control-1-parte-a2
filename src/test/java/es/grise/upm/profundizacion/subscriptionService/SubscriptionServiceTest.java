package es.grise.upm.profundizacion.subscriptionService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class SubscriptionServiceTest {
	
    @Test
    public void constructorTest(){
        SubscriptionService ss = new SubscriptionService();
        assertEquals(new ArrayList<>(), ss.getSubscribers());
    }

    @Test
    public void addSubscriberNullUser(){
        SubscriptionService ss = new SubscriptionService();
        assertThrows(NullUserException.class, ()-> ss.addSubscriber(null));
    }

    @Test
    public void addSubscriberExistingUser() throws NullUserException, 
                                                   ExistingUserException, 
                                                   LocalUserDoesNotHaveNullEMailException{
        SubscriptionService ss = new SubscriptionService();
        User u = mock(User.class);
        ss.addSubscriber(u);
        assertThrows(ExistingUserException.class, ()-> ss.addSubscriber(u));
    }

    @Test
    public void addSubscriberLocalUserNullEmail() throws NullUserException, 
                                                   ExistingUserException, 
                                                   LocalUserDoesNotHaveNullEMailException{
        SubscriptionService ss = new SubscriptionService();
        User u = mock(User.class);
        when(u.getDelivery()).thenReturn(Delivery.LOCAL);
        when(u.getEmail()).thenReturn("notNullEmail@email.com");
        assertThrows(LocalUserDoesNotHaveNullEMailException.class, ()-> ss.addSubscriber(u));
    }

    @Test
    public void addSubscriberLocalCorrect() throws NullUserException, 
                                                   ExistingUserException, 
                                                   LocalUserDoesNotHaveNullEMailException{
        SubscriptionService ss = new SubscriptionService();
        User u = mock(User.class);
        when(u.getDelivery()).thenReturn(Delivery.LOCAL);
        when(u.getEmail()).thenReturn(null);
        ss.addSubscriber(u);
        assertEquals(1, ss.getSubscribers().size());
    }

    @Test
    public void addSubscriberNotLocalCorrect() throws NullUserException, 
                                                   ExistingUserException, 
                                                   LocalUserDoesNotHaveNullEMailException{
        SubscriptionService ss = new SubscriptionService();
        User u = mock(User.class);
        when(u.getDelivery()).thenReturn(Delivery.DO_NOT_DELIVER);
        ss.addSubscriber(u);
        assertEquals(1, ss.getSubscribers().size());
    }
}
