package my.project.juja.controller.commands.utils;

import my.project.juja.testutils.JujaUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nikol on 9/27/2016.
 */
public class JujaUtilsTest {

    @Test
    public void toSetInteger(){
        String[] array = {"1", "2", "3"};
        Set<Integer> set = JujaUtils.toSetInteger(array);
        assertEquals("[1, 2, 3]", set.toString());
    }

    @Test(expected = RuntimeException.class)
    public void validateSetSizeEqualZero(){
        Set set = new HashSet<>();
        JujaUtils.validate(set, 1);
    }

    @Test(expected = RuntimeException.class)
    public void validateSetMoreThanIndex(){
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        JujaUtils.validate(set, 1);
    }

    @Test(expected = RuntimeException.class)
    public void validate(){
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(100);
        JujaUtils.validate(set, 2);
    }

    @Test(expected = RuntimeException.class)
    public void validate2(){
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(-2);
        JujaUtils.validate(set, 2);
    }
}
