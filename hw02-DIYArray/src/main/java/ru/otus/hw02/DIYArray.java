package ru.otus.hw02;

import java.util.*;

/**
 * DIYArray is resizable-array implementation of java.util.List interface.
 * Implementation of java.util.List is partial this means implementation
 * is enough to perform java.util.Collections {sort, copy, addAll}.
 *
 * Implementation is based at backing Object[]. Every time when user requires
 * add new element implementation checks memory usage. If backed array is full
 * DIYArray doubles its capacity. {@see getCapacity, size}.
 *
 * Collection isn't ordered and isn't sorted. Collection is mutable.
 *
 * @param <Value> is any reference type. Null is not forbidden
 */
public class DIYArray<Value> implements List<Value>{
	/**
	 * Creates empty array
	 * Required by Collection java doc
	 */
	public DIYArray(){
		data = allocBySize(INIT_CAPACITY);
		this.capacity = INIT_CAPACITY;
		this.size = 0;
	}

	public DIYArray(int capacity){
		data = allocBySize(capacity);
		this.capacity = capacity;
		this.size = 0;
	}

	/**
	 * Makes copy of passed collection
	 * Required by javadoc
	 * @param collection
	 */
	public void DIYArray(Collection<? extends Value> collection){
		throw new UnsupportedOperationException();
	}

	@Override
	public int size(){
		return size;
	}

	public int getCapacity(){
		return capacity;
	}

	@Override
	public boolean isEmpty(){
		if(size == 0){
			return true;
		}

		return false;
	}

	@Override
	public boolean contains(Object o){
		for(int i = 0; i < size; i++){
			Object element = data[i];
			if(Objects.equals(element, o)){
				return true;
			}
		}

		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterator<Value> iterator() {
		return new DIYIter();
	}

	@Override
	public Object[] toArray(){
		return Arrays.copyOf(data, size);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a){

		// мы не помещаемся в a
		if(a.length < size){
			return (T[]) Arrays.copyOf(data, size, a.getClass());
		}

		// мы помещаемся в a
		System.arraycopy(data, 0, a, 0, size);
		if(a.length > size){
			a[size] = null;
		}

		return a;
	}

	@Override
	public boolean add(Value e){
		if(size == capacity){
			data = grow();
		}
		data[size] = e;
		size += 1;
		return true;
	}

	@Override
	public boolean remove(Object o){
		for(int i = 0; i < size; i++){
			if(Objects.equals(data[i], o)){
				removeByIndexWithLeftShift(i);
				return true;
			}
		}

		return false;
	}

	@Override
	public ListIterator<Value> listIterator() {
		return new DIYListIterator<>();
	}

	@Override
	public ListIterator<Value> listIterator(int index) {
		return new DIYListIterator<>(index);
	}

	@Override
	public Value get(int index) {
		if(isIndexInBound(index)){
			return (Value) data[index];
		}

		throw new ArrayIndexOutOfBoundsException("Size: " + size + ". Index: " + index);
	}

	@Override
	public Value set(int index, Value element) {
		if(isIndexInBound(index)){
			Value old = (Value) data[index];
			data[index] = element;
			return old;

		}

		throw new ArrayIndexOutOfBoundsException("Size: " + size + ". Index: " + index);
	}

	@Override
	public String toString(){
		if(size == 0){
			return "[  ]";
		}

		StringBuilder bld = new StringBuilder(size);

		bld.append("[ ");
		bld.append(Objects.toString(data[0]));
		for(int i = 1; i < size; i++){
			bld.append(" ");
			bld.append(Objects.toString(data[i]));
		}
		bld.append(" ]");

		return bld.toString();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Value> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends Value> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}


	@Override
	public void add(int index, Value element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Value remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}


	@Override
	public List<Value> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	private boolean isIndexInBound(int index){
		if(index >= 0 && index < size()){
			return true;
		}

		return false;
	}

	private void removeByIndexWithLeftShift(int index){
		if(size == capacity){
			size -= 1;
			return;
		}

		System.arraycopy(data, index + 1, data, index, size - index);
		size -= 1;
	}

	@SuppressWarnings("unchecked")
	private Value[] grow(){
		capacity *= GROW_FACTOR;
		return  (Value[]) Arrays.copyOf(data, capacity);
	}

	private Object[] allocBySize(int size) {
		return new Object[size];
	}

	private class DIYIter<E> implements Iterator<E> {

		public DIYIter(){
			position = 0;
		}

		@Override
		public boolean hasNext() {
			return isIndexInBound(position);
		}

		@Override
		public E next() {
			if(!isIndexInBound(position)){
				throw new NoSuchElementException("end of array was reached");
			}

			E v = (E) data[position];
			position += 1;
			return v;
		}

		protected int getPosition() {
			return position;
		}

		protected void setPosition(int position) {
			this.position = position;
		}

		private int position;
	}

	private class DIYListIterator<E> extends DIYIter<E> implements ListIterator<E>{
		public DIYListIterator(){
			super();
		}

		public DIYListIterator(int index){
			super();
			setPosition(index);
		}

		@Override
		public boolean hasPrevious() {
			if(!isEmpty() & getPosition() != 0){
				return true;
			}

			return false;
		}

		@Override
		public E previous() {
			if(hasPrevious()){
				throw new NoSuchElementException();
			}

			if(getPosition() >= size()){
				setPosition(size() - 1);
			}

			E val = (E) data[getPosition()];
			lastReturnedPosition = getPosition();
			setPosition(getPosition() - 1);
			return val;
		}

		@Override
		public E next(){
			E val = super.next();
			lastReturnedPosition = getPosition() - 1;
			return val;
		}

		@Override
		public int nextIndex() {
			return getPosition() + 1;
		}

		@Override
		public int previousIndex() {
			return getPosition() - 1;
		}

		@Override
		public void set(E e){
			data[lastReturnedPosition] = e;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void add(E e) {
			throw new UnsupportedOperationException();
		}
	}
	private int lastReturnedPosition;
	private Object[] data;
	private int size = 0;
	private int capacity = 0;
	private static int INIT_CAPACITY = 10;
	private static int GROW_FACTOR = 2;

	public static void main(String[] args) {
		System.out.println("DIY array");
	}
}
