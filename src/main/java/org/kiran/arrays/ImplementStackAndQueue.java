class MyStack {
    
    private int[] stack = null;
    private int index = 0;
    private int capacity = 0;
    private int size = 0;

    public MyStack(int capacity) {
        this.capacity = capacity;
        stack = new int[capacity];
    }
    
    public void push(int x) {
        if(size == stack.length)    resize();
        stack[index++] = x;
        size++;
    }

    public int pop() {
        if(index == 0) 
            throw new IndexOutOfBoundsException();
        size--;
        return stack[--index];
    }

    public int peek() {
        if(index == 0) 
            throw new IndexOutOfBoundsException();
        return stack[index-1];
    }

    private void resize() {
        capacity = capacity << 1;
        if(capacity < 0)
            throw new Exception("Max capacity reached!");
        int[] newStack = new int[capacity];
        for(int i=0; i<index; i++) {
            newStack[i] = stack[i];
        }
        stack = newStack;
    }

}

class MyQueue {

    private int[] queue = null;
    private int head = 0;
    private int tail = 0;
    private int capacity = 0;

    public MyQueue(int capacity) {
        this.capacity = capacity;
        queue = new int[capacity];
    }

    public void enqueue(int x) {
        if(size == stack.length) resize();
        stack[head] = x;
        head = (head + 1)%queue.length;
        size++;
    }

    public int dequeue() {
        if(size == 0)
            throw new Exception("Empty queue");
        int x = queue[tail];
        tail = (tail + 1)%queue.length;
        size--;
        return x;
    }

    public int peek() {
        return queue[tail];
    }

    public void resize() {
        capacity = capacity << 1;
        int[] newQ = new int[newCap];
        if(capacity < 0)
            throw new Exception("Max capacity reached!");
    }

}