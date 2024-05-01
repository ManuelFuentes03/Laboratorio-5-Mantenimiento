package org.mps.boundedqueue;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ArrayBoundedQueueTest {
    
    @Nested
    @DisplayName("Test al método isFull")
    class test_isFull {
        @Test
        @DisplayName("Debe devolver True cuando la cola está completa")
        public void isFull_WhenTheQueueIsComplete_ReturnsTrue(){
            int capacity = 3;
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(capacity);
            queue.put(1);
            queue.put(2);
            queue.put(3);

            boolean result = queue.isFull();

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Debe devolver False cuando la cola no está completa")
        public void isFull_WhenTheQueueIsNotComplete_ReturnsFalse(){
            int capacity = 3;
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(capacity);
            queue.put(1);
            queue.put(2);

            boolean result = queue.isFull();

            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Test al método isEMpty")
    class test_isEmpty {
        @Test
        @DisplayName("Debe devolver True cuando la cola no contiene elementos")
        public void isEmpty_WhenTheQueueNotContainsAnything_ReturnsTrue(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(3);
            
            boolean result = queue.isEmpty();

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("Debe devolver False cuando la cola contiene elementos")
        public void isEmpty_WhenTheQueueContainsElements_ReturnsFalse(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(3);
            queue.put(4);
            
            boolean result = queue.isEmpty();

            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("Test al método size")
    class test_size {
        @Test
        @DisplayName("Debe devolver el número de elementos que contiene la cola cuando contiene elementos")
        public void size_QueueWhichContainsElements_ReturnsTheNumberOfElementsOfTheQueue(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(5);
            queue.put(1);
            queue.put(2);
            queue.put(3);
            queue.put(4);

            int result = queue.size();

            assertThat(result).isEqualTo(4);
        }

        @Test
        @DisplayName("Debe devolver cero cuando la cola está vacía")
        public void size_QueueWithNoElements_ReturnsZero(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(5);

            int result = queue.size();

            assertThat(result).isZero();
        }
    }

    @Nested
    @DisplayName("Test al método getFirst")
    class test_getFirst {
        @Test
        @DisplayName("Debe devolver la posición del primer elemento que ocupa la lista cuando la cola contiene elementos")
        public void getFirst_QueueWhichContainsElements_ReturnsThePositionOfTheFirstElement(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(5);
            queue.put(1);
            queue.put(2);
            queue.put(3);
            queue.put(4);
            queue.get();
            queue.get();

            int result = queue.getFirst();

            assertThat(result).isEqualTo(2);
        }

        @Test
        @DisplayName("Debe devolver cero cuando la cola está vacía")
        public void getFirst_QueueWithNoElements_ReturnsZero(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(5);

            int result = queue.getFirst();

            assertThat(result).isZero();
        }
    }

    @Nested
    @DisplayName("Test al método getLast")
    class test_getLast{
        @Test
        @DisplayName("Debe devolver cero cuando la cola está vacía")
        public void getLast_QueueWithNoElements_ReturnsZero(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(5);

            int result = queue.getLast();

            assertThat(result).isZero();
        }

        @Test
        @DisplayName("Debe devolver el index de la siguiente posición disponible cuando la cola contiene elementos")
        public void getLast_QueueWhichContainsElements_ReturnsTheIndexOfTheNextPositionAvailable(){
            ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<Integer>(5);
            queue.put(1);
            queue.put(2);
            queue.put(3);
            queue.put(4);

            int result = queue.getLast();

            assertThat(result).isEqualTo(4);
        }
    }

}
