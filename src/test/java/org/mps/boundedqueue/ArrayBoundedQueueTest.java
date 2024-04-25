package org.mps.boundedqueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class ArrayBoundedQueueTest {
    
    // MÉTODO CONSTRUCTOR

    @Nested
    @DisplayName("Test al constructor")
    class test_Constructor {

        @DisplayName("Construye una cola delimitada vacía.")
        @Test
        public void constructor_WithPositiveCapacity_CreateEmptyQueue(){
            int capacidad = 5;

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(capacidad);

            assertThat(array.size()).isEqualTo(0);
        }

        @DisplayName("Construye una cola delimitada vacía con la capacidad especificada.")
        @Test
        public void constructor_WithPositiveCapacity_CreateQueueWithSpecifiedCapacity(){
            int capacidad = 5;

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(capacidad);
            array.put(3);
            array.put(2);
            array.put(4);
            array.put(5);
            array.put(1);

            assertThat(array.size()).isEqualTo(capacidad);
        }

        @DisplayName("Lanza una excepción si la capacidad es igual que 0.")
        @Test
        public void constructor_WithZeroCapacity_ThrowsIllegalArgumentException(){
            int capacidad = 0;
            
            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> { new ArrayBoundedQueue<>(capacidad);})
            .withMessage("ArrayBoundedException: capacity must be positive");

        }

        @DisplayName("Lanza una excepción si la capacidad es menor que 0.")
        @Test
        public void constructor_WithNegativeCapacity_ThrowsIllegalArgumentException(){
            int capacidad = -1;
            
            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> { new ArrayBoundedQueue<>(capacidad);})
            .withMessage("ArrayBoundedException: capacity must be positive");

        }

    }

    // MÉTODO PUT
    
    @Nested
    @DisplayName("Test al método put")
    class test_Put {

        @DisplayName("El método put añade correctamente un elemento a la cola vacía.")
        @Test
        public void put_AddValueToEmptyQueue_Properly(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);

            assertThat(array.size()).isEqualTo(1);

        }

        @DisplayName("El método put añade correctamente varios elementos a la cola vacía.")
        @Test
        public void put_AddValuesToEmptyQueue_Properly(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);
            array.put(5);
            array.put(7);

            assertThat(array.size()).isGreaterThan(0);

        }

        @DisplayName("El método put lanza una FullBoundedQueueException si la cola está llena.")
        @Test
        public void put_AddValueToFullyQueue_ThrowsFullBoundedQueueException(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);
            array.put(5);
            array.put(7);
            array.put(4);
            array.put(5);

            assertThatExceptionOfType(FullBoundedQueueException.class)
            .isThrownBy(() -> { array.put(7); })
            .withMessage("put: full bounded queue");

        }

        @DisplayName("El método put lanza una IllegalArgumentException si el elemento es nulo.")
        @Test
        public void put_AddNullValueToQueue_ThrowsIllegalArgumentException(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);
            array.put(5);

            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> { array.put(null); })
            .withMessage("put: element cannot be null");

        }
    }

    // MÉTODO GET

    @Nested
    @DisplayName("Test al método get")
    class test_Get {

        @DisplayName("El método get devuelve correctamente el primer elemento de una cola con un elemento.")
        @Test
        public void get_QueueWithOneValue_ReturnsFirstElement(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);

            assertThat(array.get()).isEqualTo(4);

        }

        @DisplayName("El método get devuelve correctamente el primer elemento de una cola con varios elementos.")
        @Test
        public void get_QueueWithValues_ReturnsFirstElement(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);

            assertThat(array.get()).isEqualTo(5);

        }

        @DisplayName("El método get reduce en uno la capacidad de la cola.")
        @Test
        public void get_QueueWithValues_SubtractOneFromTheCapacity(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);
            int tamActual = array.size();

            array.get();

            assertThat(array.size()).isEqualTo(tamActual - 1);

        }

        @DisplayName("El método get elimina el primer elemento de la cola al devolverlo.")
        @Test
        public void get_ConvertsFirstElementToNull_InTheQueue(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);
            array.get();

            assertThat(array.get()).isEqualTo(4);

        }

        @DisplayName("El método get lanza una EmptyBoundedQueueException si la cola está vacía")
        @Test
        public void get_EmptyQueue_ThrowsEmptyBoundedQueueException(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            assertThatExceptionOfType(EmptyBoundedQueueException.class)
            .isThrownBy(() -> { array.get(); })
            .withMessage("get: empty bounded queue");

        }
    }
}
