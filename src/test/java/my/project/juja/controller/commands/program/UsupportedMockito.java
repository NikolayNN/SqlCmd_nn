package my.project.juja.controller.commands.program;

import my.project.juja.controller.commands.Command;
import my.project.juja.model.Storeable;
import my.project.juja.view.View;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

/**
 * Created by Nikol on 9/26/2016.
 */
public class UsupportedMockito  {
   View view;

    @Before
    public void setup(){
        Mockito.mock(View.class);
    }

    @Test
    public void test(){
        //given
       Command command = new Unsupported(view);
        //when
        String s = command.getName();
//        then
        assertEquals("", s);
    }
}
