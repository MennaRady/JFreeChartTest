package org.jfree.data;
import org.jfree.data.xy.MatrixSeries;
import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixSeriesTest
{
	// all tests will more or less follow the Arrange, Act, Assert pattern.
	@Test
	public void getColumnsCountTest()
	{
		// arrange
		
		MatrixSeries mat1 = new MatrixSeries("test matrix 1", 3, 10);
		MatrixSeries mat2 = new MatrixSeries("test matrix 2", 8, 16);
		MatrixSeries mat3 = new MatrixSeries("test matrix 3", 100, 99);
		MatrixSeries mat4 = new MatrixSeries("test matrix 4", 1, 1);
		MatrixSeries mat5 = new MatrixSeries("test matrix 5", 1, 0);
		// act
		int mat1ColumnCount = mat1.getColumnsCount();
		int mat2ColumnCount = mat2.getColumnsCount();
		int mat3ColumnCount = mat3.getColumnsCount();
		int mat4ColumnCount = mat4.getColumnsCount();
		int mat5ColumnCount = mat5.getColumnsCount();
		// assert
		assertEquals(mat1ColumnCount, 10);
		assertEquals(mat2ColumnCount, 16);
		assertEquals(mat3ColumnCount, 99);
		assertEquals(mat4ColumnCount, 1);
		assertEquals(mat5ColumnCount, 0);
		
	}
	
	@Test
	public void getRowCountTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix 1", 3, 10);
		MatrixSeries mat2 = new MatrixSeries("test matrix 2", 8, 16);
		MatrixSeries mat3 = new MatrixSeries("test matrix 3", 100, 99);
		MatrixSeries mat4 = new MatrixSeries("test matrix 4", 1400, 1);
		MatrixSeries mat5 = new MatrixSeries("test matrix 5", 1, 0);
		
		
		int mat1RowCount = mat1.getRowCount();
		int mat2RowCount = mat2.getRowCount();
		int mat3RowCount = mat3.getRowCount();
		int mat4RowCount = mat4.getRowCount();
		int mat5RowCount = mat5.getRowCount();
		
		assertEquals(mat1RowCount, 3);
		assertEquals(mat2RowCount, 8);
		assertEquals(mat3RowCount, 100);
		assertEquals(mat4RowCount, 1400);
		assertEquals(mat5RowCount, 1);
	}
	
	@Test
	public void getItemColumnTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix 1", 3, 3);
		assertEquals(0, mat1.getItemColumn(0));
		assertEquals(1, mat1.getItemColumn(1));
		assertEquals(2, mat1.getItemColumn(2));
		assertEquals(0, mat1.getItemColumn(3));
		assertEquals(1, mat1.getItemColumn(4));
	}
	
	@Test(expected = ArithmeticException.class)
	public void getItemRowTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix 1", 3, 3);
		MatrixSeries mat2 = new MatrixSeries("test matrix 2", 3, 0);
		
		assertEquals(0, mat1.getItemRow(0));
		assertEquals(0, mat1.getItemRow(1));
		assertEquals(0, mat1.getItemRow(2));
		assertEquals(1, mat1.getItemRow(3));
		assertEquals(1, mat1.getItemRow(4));
		assertEquals(1, mat1.getItemRow(5));
		assertEquals(2, mat1.getItemRow(6));
		assertEquals(2, mat1.getItemRow(7));
		assertEquals(2, mat1.getItemRow(8));
		
		assertEquals(0, mat2.getItemRow(0));
		assertEquals(0, mat2.getItemRow(1));
		assertEquals(0, mat2.getItemRow(2));
		assertEquals(1, mat2.getItemRow(3));
		assertEquals(1, mat2.getItemRow(4));
		assertEquals(1, mat2.getItemRow(5));
		assertEquals(2, mat2.getItemRow(6));
		assertEquals(2, mat2.getItemRow(7));
		assertEquals(2, mat2.getItemRow(8));
	}
	
	@Test
	public void getItemCountTest()
	{
		// boundary for a matrix (according to jfree) is
		// 1x0 matrix lower-boundary and upper-bound depends on how much memory
		// is permitted for the program.
		// matrix has to have at least 1 row but can have 0 columns.
		MatrixSeries mat1 = new MatrixSeries("test matrix 1", 3, 3);
		MatrixSeries mat2 = new MatrixSeries("test matrix 2", 100, 100);
		MatrixSeries mat3 = new MatrixSeries("test matrix 3", 900, 1400);
		MatrixSeries mat4 = new MatrixSeries("test matrix 4", 1, 1);
		MatrixSeries mat5 = new MatrixSeries("test matrix 6", 1, 0);
		
		assertEquals(9, mat1.getItemCount());
		assertEquals(10000, mat2.getItemCount());
		assertEquals(1260000, mat3.getItemCount());
		assertEquals(1, mat4.getItemCount());
		assertEquals(0, mat5.getItemCount());
	}
	
	@Test
	public void zeroAllTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix 1", 3, 3);
		MatrixSeries mat2 = new MatrixSeries("test matrix 2", 3, 9);
		MatrixSeries mat3 = new MatrixSeries("test matrix 3", 800, 1400);
		mat1.zeroAll();
		
		boolean isMat1AllZero = true;
		boolean isMat2AllZero = true;
		boolean isMat3AllZero = true;
		
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 3; ++j)
				if(mat1.get(i, j) != 0)
					isMat1AllZero = false;
		
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 9; ++j)
				if(mat2.get(i, j) != 0)
					isMat2AllZero = false;
		
		for(int i = 0; i < 800; ++i)
			for(int j = 0; j < 1400; ++j)
				if(mat3.get(i, j) != 0)
					isMat3AllZero = false;
		
		
		assertTrue(isMat1AllZero);
		assertTrue(isMat2AllZero);
		assertTrue(isMat3AllZero);
	}
	
	@Test
	public void equalsTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix", 4, 4);
		MatrixSeries mat2 = new MatrixSeries("test matrix", 4, 4);
		MatrixSeries mat3 = new MatrixSeries("test matrix", 101, 101);
		MatrixSeries mat4 = new MatrixSeries("test matrix", 101, 100);
		MatrixSeries mat5 = new MatrixSeries("test matrix", 101, 100);
		
		mat1.update(0, 0, 1);
		mat1.update(1, 1, 2);
		mat1.update(2, 2, 3);
		mat1.update(3, 3, 5);
		mat2.update(0, 0, 1);
		mat2.update(1, 1, 2);
		mat2.update(2, 2, 3);
		mat2.update(3, 3, 5);
		
		assertTrue(mat1.equals(mat2));
		
		mat3.update(1, 1, 100);
		mat4.update(1, 1, 100);
		mat5.update(1, 1, 100);
		
		assertFalse(mat3.equals(mat4));
		assertTrue(mat4.equals(mat5));
		
		mat2.update(3, 3, 4);
		assertFalse(mat1.equals(mat2));
		
		mat1.update(3, 3, 4);
		assertTrue(mat1.equals(mat2));
		mat1 = new MatrixSeries("test matrix", 4, 4);
		assertFalse(mat1.equals(mat2));
	}
	
	
}
