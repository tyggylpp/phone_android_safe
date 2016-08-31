package com.dragon.phone_mobilesafe;

import android.content.Context;
import android.test.AndroidTestCase;

import com.dragon.phone_mobilesafe.db.ado.BlackNumberDao;

import junit.extensions.TestSetup;

import org.junit.Test;

import java.util.Random;

/**
 * Created by pc on 2016-08-04.
 */
public class BlackNumberTest extends  AndroidTestCase{
   Context m_context;

    @Override
    protected void setUp() throws Exception {

        super.setUp();
        m_context=this.getContext();

    }

    @Override
    public void testAndroidTestCaseSetupProperly() {
        super.testAndroidTestCaseSetupProperly();
    }

    public void add()
   {
 int a=1/0;
  assertEquals(a,1);
   }

  public  void BlackNumberAdd()
   {
      m_context= getContext();
      BlackNumberDao dao=new BlackNumberDao(m_context);
       assertNull(dao);
       assertEquals(1,2);
       Random random=new Random();
      for (int i = 0; i < 200; i++) {
          long number= 13000000000l+i;
         dao.add(""+number,String.valueOf(random.nextInt(3)+1));
      }
   }
}
