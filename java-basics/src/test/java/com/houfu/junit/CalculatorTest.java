package com.houfu.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    //每一个测试方法执行前执行 setUp 方法
    //每个测试都拿到一个全新的对象，测试之间要相互独立。
    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
//    @DisplayName("加法 - 两个正数相加")
    void add_twoPositiveNumbers_returnsSum() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("加法 - 包含负数")
    void add_withNegativeNumber() {
        assertEquals(-1, calculator.add(2, -3));
        assertEquals(0, calculator.add(-5, 5));
    }

    @Test
    @DisplayName("减法 - 基本运算")
    void subtract_basic() {
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-2, calculator.subtract(3, 5));
    }

    @Test
    @DisplayName("乘法 - 包含零和负数")
    void multiply_withZeroAndNegative() {
        assertEquals(0, calculator.multiply(10, 0));
        assertEquals(6, calculator.multiply(-2, -3));
        assertEquals(-6, calculator.multiply(2, -3));
    }

    @Test
    @DisplayName("除法 - 正常计算")
    void divide_normal() {
        assertEquals(2, calculator.divide(10, 5));
    }

    @Test
    @DisplayName("除法 - 除以零应抛出异常")
    void divide_byZero_throwsException() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
    }

    /*
    原始写法：
        new Runnable() {
        @Override
        public void run() {
            calculator.divide(10, 0);
        }
    };
    */

    @Test
    @DisplayName("isEven - 判断奇偶")
    void isEven_checkEvenAndOdd() {
        assertTrue(calculator.isEven(4));
        assertFalse(calculator.isEven(3));
        assertTrue(calculator.isEven(0));
        assertTrue(calculator.isEven(-2));
    }

    @Test
    @DisplayName("max - 取最大值")
    void max_twoNumbers() {
        assertEquals(10, calculator.max(10, 5));
        assertEquals(10, calculator.max(5, 10));
        assertEquals(5, calculator.max(5, 5));
    }

    @Test
    @DisplayName("多断言组合验证")
    void combinedAssertions() {
        assertAll("Calculator 基本运算验证",
                () -> assertEquals(4, calculator.add(1, 3)),
                () -> assertEquals(2, calculator.subtract(5, 3)),
                () -> assertEquals(6, calculator.multiply(2, 3))
        );
    }

    // ==================== @ParameterizedTest 参数化测试 ====================

    @ParameterizedTest
    @ValueSource(ints = {0, 2, 4, 100, -6})
    @DisplayName("参数化：多个偶数输入都能正确判断")
    void isEven_参数化测试(int n) {
        assertTrue(calculator.isEven(n), () -> n + " 应该是偶数");
    }

    @ParameterizedTest
    @CsvSource({"2,3,5", "0,0,0", "-1,1,0", "10,20,30"})
    @DisplayName("参数化：多组加法输入")
    void add_多组数据(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    // ==================== @Tag 打标签 ====================

    @Tag("fast")
    @Test
    @DisplayName("带标签：快速测试")
    void taggedTest_fast() {
        assertEquals(1, calculator.divide(10, 10));
    }

    // ==================== @Disabled 跳过 ====================

    @Disabled("除法还没处理小数情况，暂时跳过")
    @Test
    @DisplayName("带跳过：暂时不运行")
    void disabledTest() {
        assertEquals(1.5, calculator.divide(3, 2));
    }

    // ==================== @Nested 嵌套测试类 ====================

    @Nested
    @DisplayName("除法相关测试（嵌套分组）")
    class DivisionTests {

        private Calculator calc;

        @BeforeEach
        void init() {
            calc = new Calculator();
        }

        @Test
        @DisplayName("正常除法")
        void normalDivision() {
            assertEquals(2, calc.divide(10, 5));
        }

        @Test
        @DisplayName("除以零应抛异常")
        void divideByZeroThrows() {
            assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
        }
    }
}