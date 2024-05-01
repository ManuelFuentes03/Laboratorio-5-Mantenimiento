package org.mps.boundedqueue;

import static org.assertj.core.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ArrayBoundedQueueTest {

    @Nested
    @DisplayName("Probamos el constructor del iterador")
    class test_ArrayBoundedQueueIterator {
        @Test
        @DisplayName("Comprobamos que el meétodo ArrayBoundedQueueIterator se inicializa correctamente")
        public void iterator_newArrayBoundedQueueIterator_WorksProperly(){
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            cola.put(4);
            cola.put(5);

            Iterator<Integer> iterador = cola.iterator();

            assertThat(iterador)
                .isNotNull();
        }
        
    }

    @Nested
    @DisplayName("Probamos el método hasNext del iterador")
    class test_hasNext{

        @Test
        @DisplayName("Devuelve true si el elemento de la lista no es el último")
        public void hasNext_WhenIsNotTheLastObject_ReturnTrue(){
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            cola.put(4);
            cola.put(5);

            boolean resultado = cola.iterator().hasNext();

            assertThat(resultado)
                .isTrue();
        }

        @Test
        @DisplayName("Devuelve false si el elemento de la lista es el último")
        public void hasNext_WhenIsTheLastObject_ReturnFalse(){
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(4);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            cola.put(4);

            Iterator<Integer> iterador = cola.iterator();
            iterador.next();
            iterador.next();
            iterador.next();
            iterador.next();
            boolean resultado = iterador.hasNext();

            assertThat(resultado)
                .isFalse();
        }
        
    }

    @Nested
    @DisplayName("Probamos el método next del iterador")
    class test_next {
        @Test
        @DisplayName("Devuelve el siguiente elemento de la cola")
        public void next_ThereIsAElementInNextPosition_ReturnNextElement(){
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            cola.put(4);
            cola.put(5);

            Iterator<Integer> iterador = cola.iterator();
            iterador.next();
            iterador.next();
            int siguiente = iterador.next();
            
            assertThat(siguiente)
                .isEqualTo(3);
        }

        @Test
        @DisplayName("Lanza una excepción si el elemento no tiene siguiente")
        public void next_NoHaveNextElement_ThrowsException(){
            ArrayBoundedQueue<Integer> cola = new ArrayBoundedQueue<>(5);
            cola.put(1);
            cola.put(2);
            cola.put(3);
            cola.put(4);
            cola.put(5);

            Iterator<Integer> iterador = cola.iterator();
            iterador.next();
            iterador.next();
            iterador.next();
            iterador.next();
            iterador.next();
            
            assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> {throw new NoSuchElementException("next: bounded queue iterator exhausted");})
                .withMessage("next: bounded queue iterator exhausted");
        }
        
    }
    

    
    
    
}
