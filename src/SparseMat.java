import java.util.ListIterator;

import cs_1c.FHarrayList;
import cs_1c.FHlinkedList;


//--------------- Class SparseMat Definition ---------------
public class SparseMat<E> implements Cloneable
{
   // protected enables us to safely make col/data public
   protected class MatNode implements Cloneable
   {
      public int col;
      public E data;
      
      // we need a default constructor for lists
      MatNode()
      {
         col = 0;
         data = null;
      }

      MatNode(int cl, E dt)
      {
         col = cl;
         data = dt;
      }
      
      public Object clone() throws CloneNotSupportedException
      {
         // shallow copy
         MatNode newObject = (MatNode)super.clone();
         return (Object) newObject;
      }
   };
   
   protected int rowSize, colSize;
   protected E defaultVal;
   protected FHarrayList < FHlinkedList< MatNode > > rows;
   
   public int getRowSize() { return rowSize; }
   public int getColSize() { return colSize; }
   
   // constructor creates an empty Sublist (no indices)
   public SparseMat( int numRows, int numCols, E defaultVal)
   {
      if ( numRows < 1 || numCols < 1 )
         throw new IllegalArgumentException();
      
      rowSize = numRows;
      colSize = numCols;
      allocateEmptyMatrix();
      this.defaultVal = defaultVal;
   }
   
   protected void allocateEmptyMatrix()
   {
      int r;
      rows = new FHarrayList < FHlinkedList< MatNode > >();
      for (r = 0; r < rowSize; r++)
         rows.add( new FHlinkedList< MatNode >());   // add a blank row
   }
   
   public void clear()
   {
      int r;

      for (r = 0; r < rowSize; r++)
         rows.get(r).clear();
   }
   
   // optional method - good practice for java programmers
   public Object clone() throws CloneNotSupportedException
   {
      int r;
      ListIterator<MatNode> iter;
      FHlinkedList < MatNode > newRow;
      
      // shallow copy
      SparseMat<E> newObject = (SparseMat<E>)super.clone();
      
      // create all new lists for the new object
      newObject.allocateEmptyMatrix();
     
      // clone
      for (r = 0; r < rowSize; r++)
      {
         newRow = newObject.rows.get(r);
         // iterate along the row, looking for column c
         for (
               iter =
                  (ListIterator<MatNode>)rows.get(r).listIterator();
            iter.hasNext(); )
         {
            newRow.add( (MatNode) iter.next().clone() );
         }
      }
      
      return newObject;
   }
   
   protected boolean valid(int r, int c)
   {
      if (r >= 0 && r < rowSize && c >= 0 && c < colSize)
         return true;
      return false;
   }
   
   public boolean set(int r, int c, E x)
   {
      if (!valid(r, c))
         return false;

      ListIterator<MatNode> iter;

      // iterate along the row, looking for column c
      for (iter =
         (ListIterator<MatNode>)rows.get(r).listIterator();
         iter.hasNext(); )
      {
         if ( iter.next().col == c )
         {
            if ( x.equals(defaultVal) )
               iter.remove();
            else
               iter.previous().data = x;
            return true;
         }
      }

      // not found
      if ( !x.equals(defaultVal) )
         rows.get(r).add( new MatNode(c, x) );
      return true;
   }
   
   public E get(int r, int c)
   {
      if (!valid(r, c))
         throw new IndexOutOfBoundsException();

      ListIterator<MatNode> iter;

      // iterate along the row, looking for column c
      for (iter =
         (ListIterator<MatNode>)rows.get(r).listIterator();
         iter.hasNext(); )
      {
         if ( iter.next().col == c )
            return iter.previous().data;
      }
      // not found
      return defaultVal;
   }
   
   public void showSubSquare(int start, int size)
   {
      int r, c;

      if (start < 0 || size < 0
         || start + size > rowSize
         || start + size > colSize )
         return;

      for (r = start; r < start + size; r++)
      {
         for (c = start; c < start + size; c++)
            System.out.print( String.format("%4.1f", (Double)get(r, c)) + " " );
         System.out.println();
      }
      System.out.println();
   }
}
