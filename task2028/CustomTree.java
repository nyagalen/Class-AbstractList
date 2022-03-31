package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;

/* 
Построй дерево(1)
*/

public class  CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    List<Entry<String>> list = new LinkedList<>();
    int listSize = 0;

    public CustomTree() {
        this.root = new Entry<String>("0");
        root.parent = root;
        list.add(root);
    }

    static class Entry<T> implements Serializable{

        String elementName;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;

        Entry<T> parent;
        Entry<T>  leftChild;
        Entry<T> rightChild;

       boolean isRight;
       boolean isLeft ;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;

        }
        public boolean isAvailableToAddChildren(){
            return availableToAddLeftChildren||availableToAddRightChildren;
        }



    }
    @Override
    public int size() {
        return listSize;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }
    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }



    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }


        @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }


    @Override
    public boolean add(String s) {
        Entry<String> entry = new Entry<>(s);
        boolean possibleToAdd = false;
        for (int i = 0; i<list.size(); i++){
            if(list.get(i)!=null&&list.get(i).isAvailableToAddChildren()){
                possibleToAdd = true;
                break;
            }
        }
        if (!possibleToAdd&&!list.isEmpty()){
            for(int i = 0; i<list.size(); i++){
                Entry<String> current = list.get(i);
                if(current.leftChild==null&&current.rightChild==null) {
                    current.availableToAddLeftChildren=true;
                    current.availableToAddRightChildren=true;
                }
            }

        }
        for (int i = 0; i<list.size(); i++){
            Entry<String> potentialParent = list.get(i);
            if (potentialParent.isAvailableToAddChildren()) {
                entry.parent = potentialParent;
                if(potentialParent.availableToAddLeftChildren) {
                    list.add(entry);
                    entry.isLeft = true;
                    potentialParent.leftChild = entry;
                  //  potentialParent.hasChildren = true;
                    listSize++;
                    potentialParent.availableToAddLeftChildren=false;
                    return true;
                } else if(potentialParent.availableToAddRightChildren){
                    list.add(entry);
                    entry.isRight = true;
                    potentialParent.rightChild = entry;
                   // potentialParent.has
                    listSize++;
                    potentialParent.availableToAddRightChildren = false;
                    return true;
                }
            }

        }

        return false;
    }
    public String getParent(String s){
        String parentName = null;

        for(int i = 0; i<list.size(); i++){
            Entry<String> currentEntry = list.get(i);
            if (currentEntry.elementName.equals(s)){
                parentName = currentEntry.parent.elementName;

            }
        }
        return parentName;
    }
    public void fillTheStack(Stack<Entry<String>> stack, Entry<String> entry){

        stack.push(entry);
        if(entry.leftChild!=null) fillTheStack(stack, entry.leftChild);
        if(entry.rightChild!=null) fillTheStack(stack, entry.rightChild);



    }

    public boolean remove(Object o){

        if (!(o instanceof String)) throw new UnsupportedOperationException();
        String currentName = (String) o;

        for (int i = 0; i<list.size(); i++){
            Entry<String> currentEntry = list.get(i);


            if (currentEntry.elementName.equals(currentName)){
                Stack<Entry<String>> removeMe = new Stack<>();


               fillTheStack(removeMe, currentEntry);
                for(Entry<String> entry : removeMe){
                    for(int j = 0; j<list.size(); j++){

                        if(entry.equals(list.get(j))) {
                            Entry<String> currentParent = entry.parent;
                            if(entry.isLeft){
                                currentParent.leftChild = null;
                            }else if (entry.isRight){
                                currentParent.rightChild = null;
                            }
                            list.remove(entry);
                        }

                    }




                    listSize--;
                }

                return true;
            }
        }

        return false;
    }
}
