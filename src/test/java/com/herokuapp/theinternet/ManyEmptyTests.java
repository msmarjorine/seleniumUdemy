package com.herokuapp.theinternet;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ManyEmptyTests {

    @Test(groups = { "functest", "uitest" })
    public void firstMethod(){
        System.out.println("This is the first method");
        Assert.assertEquals(1,1);
    }

    @Test(groups={"functest"})
    public void secondMethod(){
        System.out.println("This is the second method");
        Assert.assertEquals(1,1);
    }

    @Test(groups={"uitest"})
    public void thirdMethod(){
        System.out.println("This is the third method");
        Assert.assertEquals(1,1);
    }

    @Test(groups={"functest"})
    public void fourthMethod(){
        System.out.println("This is the fourth method");
        Assert.assertEquals(1,1);
    }

    @Test(groups={"uitest"})
    public void fifthMethod(){
        System.out.println("This is the fifth method");
        Assert.assertEquals(1,1);
    }

    @Test(groups={"functest"})
    public void sixthMethod(){
        System.out.println("This is the sixth method");
        Assert.assertEquals(1,1);
    }
}
