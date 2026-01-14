package com.HimanshuBagga.TestingSpringBoot;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
@Slf4j
class TestingSpringBootApplicationTests {

    @BeforeEach
    void setup(){
        log.info("starting the method , setting up config");
    }

    @AfterEach
    void teatDown(){
        log.info("Tearing down the method ");
    }

    @BeforeAll
    static void setUpOnce(){
        log.info("Before All");
    }

    @AfterAll
    static void tearDownOnce(){
        log.info("Tearing down at last");
    }

//    @DisplayName("Try")
//    @Disabled
    @Test
	void testDivideTwoNumbers_WhenDenominatorIsZero_ThenArithmeticException() {
        int a = 5;
        int b = 0;

        assertThatThrownBy(() -> divideTwoNumbers(a,b))
                .isInstanceOf(ArithmeticException.class)
                .hasMessage("Tried to divide by zero");
	}


    @Test
    void testNumberOne() {
        int a = 5;
        int b = 5;
        int result = addTwoNumbers(a,b);
//        Assertions.assertEquals(10 , result); // is my expected output equals actual

        assertThat(result)
                .isEqualTo(10)
                .isCloseTo(15 , Offset.offset(5));

        assertThat("Apple")
                .isEqualTo("Apple")
                .startsWith("App")
                .endsWith("le")
                .hasSize(5);

    }

    int addTwoNumbers(int a , int b){
        return a+b;
    }

    double divideTwoNumbers(int a , int b){
        try{
            return a/b;
        }catch(ArithmeticException e){
            log.error("Arithmatic Exception: " + e.getLocalizedMessage());
            throw new ArithmeticException("Tried to divide by zero");
        }
    }

}
