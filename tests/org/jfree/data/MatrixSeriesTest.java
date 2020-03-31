package org.jfree.data;
import org.jfree.data.xy.MatrixSeries;
import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixSeriesTest
{
	// all tests will more or less follow the Arrange, Act, Assert pattern.
	@Test
	public void getColumnsCount_normalValueTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 10);
		int matColumnCount = mat.getColumnsCount();
		assertEquals(matColumnCount, 10);
	}
	
	@Test
	public void getColumnsCount_highValueTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 100000000);
		int matColumnCount = mat.getColumnsCount();
		assertEquals(matColumnCount, 100000000);
	}
	
	@Test
	public void getColumnsCount_lowValueTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 1);
		int matColumnCount = mat.getColumnsCount();
		assertEquals(matColumnCount, 1);
	}
	
	@Test
	public void getColumnsCount_zeroColumnTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 0);
		int matColumnCount = mat.getColumnsCount();
		assertEquals(matColumnCount, 0);
	}
	
	@Test
	public void getRowCount_normalValueTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 20, 10);
		int matRowCount = mat.getRowCount();
		assertEquals(matRowCount, 20);
	}
	
	@Test
	public void getRowCount_highValueTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 1000000, 10);
		int matRowCount = mat.getRowCount();
		assertEquals(matRowCount, 1000000);
	}
	
	@Test
	public void getRowCount_lowValueTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 1, 10);
		int matRowCount = mat.getRowCount();
		assertEquals(matRowCount, 1);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getRowCount_zeroRowTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 0, 10);
		int matRowCount = mat.getRowCount();
		assertEquals(matRowCount, 0);
	}
	
	// index less than columnCount tests
	@Test
	public void getItemColumn_zeroIndexTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(0, mat.getItemColumn(0));
	}
	
	@Test
	public void getItemColumn_oneIndexTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(1, mat.getItemColumn(1));
	}
	
	@Test
	public void getItemColumn_twoIndexTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(2, mat.getItemColumn(2));
	}
	
	// zeroOver = zero repeating because of the mod
	@Test
	public void getItemColumn_indexEqualsColumnCountTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(0, mat.getItemColumn(3));
	}
	
	@Test
	public void getItemColumn_oneIndexOverColumnCountTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(1, mat.getItemColumn(4));
	}
	
	@Test
	public void getItemRow_indexLargerThanColumnCountTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(2, mat.getItemRow(8));
	}
	
	@Test
	public void getItemRow_indexEqualsColumnCountTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 75, 112);
		assertEquals(1, mat.getItemRow(112));
	}
	
	@Test
	public void getItemRow_indexLessThanColumnsCountTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 11, 32);
		assertEquals(0, mat.getItemRow(29));
	}
	
	@Test(expected = ArithmeticException.class)
	public void getItemRow_zeroColumnsMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 41, 0);
		mat.getItemRow(5520);
	}
	
	@Test
	public void getItemCount_smallMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 3);
		assertEquals(9, mat.getItemCount());
	}
	
	@Test
	public void getItemCount_zeroColumnsTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 0);
		assertEquals(0, mat.getItemCount());
	}
	
	@Test
	public void getItemCount_largeMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 100, 100);
		assertEquals(10000, mat.getItemCount());
	}
	
	@Test
	public void getItemCount_veryLargeMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 10000, 10000);
		assertEquals(100000000, mat.getItemCount());
	}
	
	@Test
	public void zeroAll_squareMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 100, 100);
		mat.zeroAll();
		boolean isMatAllZeroes = true;
		for(int i = 0; i < 100; ++i)
			for(int j = 0; j < 100; ++j)
				if(mat.get(i, j) != 0)
					isMatAllZeroes = false;
		assertTrue(isMatAllZeroes);
	}
	
	@Test
	public void zeroAll_rectangularMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 1000, 1500);
		mat.zeroAll();
		boolean isMatAllZeroes = true;
		for(int i = 0; i < 1000; ++i)
			for(int j = 0; j < 1500; ++j)
				if(mat.get(i, j) != 0)
					isMatAllZeroes = false;
		assertTrue(isMatAllZeroes);
	}
	
	@Test
	public void zeroAll_vectorTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 3, 1);
		mat.zeroAll();
		boolean isMatAllZeroes = true;
		for(int i = 0; i < 3; ++i)
			for(int j = 0; j < 1; ++j)
				if(mat.get(i, j) != 0)
					isMatAllZeroes = false;
		assertTrue(isMatAllZeroes);
	}
	
	@Test
	public void zeroAll_largeMatrixTest()
	{
		MatrixSeries mat = new MatrixSeries("test matrix", 10000, 10001);
		mat.zeroAll();
		boolean isMatAllZeroes = true;
		for(int i = 0; i < 10000; ++i)
			for(int j = 0; j < 10001; ++j)
				if(mat.get(i, j) != 0)
					isMatAllZeroes = false;
		assertTrue(isMatAllZeroes);
	}
	
	@Test
	public void equals_equalSquareMatrixTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix", 4, 4);
		MatrixSeries mat2 = new MatrixSeries("test matrix", 4, 4);
		
		mat1.update(0, 0, 1);
		mat1.update(1, 1, 2);
		mat1.update(2, 2, 3);
		mat1.update(3, 3, 5);
		mat2.update(0, 0, 1);
		mat2.update(1, 1, 2);
		mat2.update(2, 2, 3);
		mat2.update(3, 3, 5);
		
		assertTrue(mat1.equals(mat2));
	}
	
	@Test
	public void equals_unequalSquareMatrixTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix", 4, 4);
		MatrixSeries mat2 = new MatrixSeries("test matrix", 4, 4);
		
		mat1.update(0, 0, 1);
		mat1.update(1, 1, 2);
		mat1.update(2, 2, 3);
		mat1.update(3, 3, 5);
		mat2.update(0, 0, 1);
		mat2.update(1, 1, 2);
		mat2.update(2, 2, 3);
		mat2.update(3, 3, 4);
		
		assertFalse(mat1.equals(mat2));
	}
	
	@Test
	public void equals_equalRectangularMatrixTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix", 101, 100);
		MatrixSeries mat2 = new MatrixSeries("test matrix", 101, 100);
		
		mat1.update(1, 1, 100);
		mat2.update(1, 1, 100);
		
		assertTrue(mat1.equals(mat2));
	}
	
	@Test
	public void equals_unequalRectangularMatrixTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix", 101, 100);
		MatrixSeries mat2 = new MatrixSeries("test matrix", 101, 100);
		
		mat1.update(1, 1, 100);
		mat2.update(10, 10, 100);
		
		assertFalse(mat1.equals(mat2));
	}
	
	@Test
	public void equals_differentDimensionsTest()
	{
		MatrixSeries mat1 = new MatrixSeries("test matrix", 101, 101);
		MatrixSeries mat2 = new MatrixSeries("test matrix", 101, 100);
		
		mat1.update(1, 1, 100);
		mat2.update(1, 1, 100);
		
		assertFalse(mat1.equals(mat2));
	}
	
	@Test
	public void equals_differentNamesTest()
	{
		MatrixSeries mat1 = new MatrixSeries("matrix 1", 101, 100);
		MatrixSeries mat2 = new MatrixSeries("matrix 2", 101, 100);
		
		mat1.update(1, 1, 100);
		mat2.update(1, 1, 100);
		
		assertFalse(mat1.equals(mat2));
	}
	
	
}
