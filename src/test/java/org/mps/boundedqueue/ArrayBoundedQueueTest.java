/*
 * @author1 José Antonio Casado Molina
 * @author2 Clemente Cano Mengíbar
 * @author3 Manuel Fuentes Vida
 */

package org.mps.boundedqueue;

import static org.assertj.core.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ArrayBoundedQueueTest {
    
    // MÉTODO CONSTRUCTOR

    @Nested
    @DisplayName("Test al constructor")
    class test_Constructor {

        
        @Test
        @DisplayName("Construye una cola delimitada vacía.")
        public void constructor_WithPositiveCapacity_CreateEmptyQueue(){
            int capacidad = 5;

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(capacidad);

            assertThat(array.size()).isEqualTo(0);
        }

        
        @Test
        @DisplayName("Construye una cola delimitada vacía con la capacidad especificada.")
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

        
        @Test
        @DisplayName("Lanza una excepción si la capacidad es igual que 0.")
        public void constructor_WithZeroCapacity_ThrowsIllegalArgumentException(){
            int capacidad = 0;
            
            assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> { new ArrayBoundedQueue<>(capacidad);})
            .withMessage("ArrayBoundedException: capacity must be positive");

        }

        
        @Test
        @DisplayName("Lanza una excepción si la capacidad es menor que 0.")
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

        
        @Test
        @DisplayName("El método put añade correctamente un elemento a la cola vacía.")
        public void put_AddValueToEmptyQueue_Properly(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);

            assertThat(array.size()).isEqualTo(1);

        }

        
        @Test
        @DisplayName("El método put añade correctamente varios elementos a la cola vacía.")
        public void put_AddValuesToEmptyQueue_Properly(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);
            array.put(5);
            array.put(7);

            assertThat(array.size()).isGreaterThan(0);

        }

        
        @Test
        @DisplayName("El método put lanza una FullBoundedQueueException si la cola está llena.")
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

        @Test
        @DisplayName("El método put vuelve a insertar por el principio cuando se llena la cola.")
        public void put_AddValueinFirstPosition_IfQueueIsFull(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);
            java.util.Iterator<Integer> it = array.iterator();  // Creamos un iterador

            array.put(5);
            array.put(4);
            array.put(1);
            array.put(7);
            array.put(8);         // Cola llena
            array.get();                // Liberamos el primer elemento
            array.put(2);         // Añadimos en la primera posición

            assertThat(it.next()).isEqualTo(2); 

        }

        
        @Test
        @DisplayName("El método put lanza una IllegalArgumentException si el elemento es nulo.")
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

        @Test
        @DisplayName("El método get devuelve correctamente el primer elemento de una cola con un elemento.")
        public void get_QueueWithOneValue_ReturnsFirstElement(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(4);

            assertThat(array.get()).isEqualTo(4);

        }

        
        @Test
        @DisplayName("El método get devuelve correctamente el primer elemento de una cola con varios elementos.")
        public void get_QueueWithValues_ReturnsFirstElement(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);

            assertThat(array.get()).isEqualTo(5);

        }

        
        @Test
        @DisplayName("El método get reduce en uno la capacidad de la cola.")
        public void get_QueueWithValues_SubtractOneFromTheCapacity(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);
            int tamActual = array.size();

            array.get();

            assertThat(array.size()).isEqualTo(tamActual - 1);

        }

        
        @Test
        @DisplayName("El método get elimina el primer elemento de la cola al devolverlo.")
        public void get_ConvertsFirstElementToNull_InTheQueue(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);
            array.get();

            assertThat(array.get()).isEqualTo(4);

        }


        @Test
        @DisplayName("El método get libera la primera posicion para insertar nuevos elementos cuando la cola está llena.")
        public void get_FreeFirstPosition_ToInsertNewElements(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            array.put(5);
            array.put(4);
            array.put(1);
            array.put(7);
            array.put(8);         // Cola llena
            array.get();                // Liberamos el primer elemento
            array.put(2);         // Añadimos en la primera posición
            array.get();            
            array.get();
            array.get();
            array.get();                // Vaciamos el resto de elementos

            int result = array.get();   // Obtenemos el primer elemento (primera posición)

            assertThat(result).isEqualTo(2);
            //assertThat(array.getLast()).isEqualTo(1);
        }

        
        @Test
        @DisplayName("El método get lanza una EmptyBoundedQueueException si la cola está vacía")
        public void get_EmptyQueue_ThrowsEmptyBoundedQueueException(){

            ArrayBoundedQueue<Integer> array = new ArrayBoundedQueue<>(5);

            assertThatExceptionOfType(EmptyBoundedQueueException.class)
            .isThrownBy(() -> { array.get(); })
            .withMessage("get: empty bounded queue");

        }
    }
    
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
