import Structures.F4;
import Structures.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Åsmund Hammer on 25.04.18.
 */
public class MatrixTests {

    Matrix m;

    @BeforeEach
    public void before(){
        m = Matrix.generateRandomNxNMatrix(100);
    }

    @Test
    public void matrixEqualsItself(){
        assertTrue(m.equals(m));
    }


    @Test
    public void matrixMultiplicationIdentityProperty(){
        Matrix i = Matrix.generateNxNIdentityMatrix(100);

        Matrix result = i.multiply(m);
        assertTrue(result.equals(m));
    }

    @Test
    public void matrixAdditionIdentityProperty(){
        Matrix i = new Matrix(100,100);
        i.fill(F4.ZERO);

        Matrix result = i.add(m);

        assertTrue(result.equals(m));
    }

    @Test
    public void TransposeOfTransposeIsOriginalMatrix(){
        Matrix t = m.transpose();
        Matrix t2 = t.transpose();

        assertTrue(t2.equals(m));
    }

    @Test
    public void TransposeRowEqualsColumn(){
        Matrix t = m.transpose();
        for (int j = 0; j < t.getRows(); j++) {

            for (int i = 0; i < t.getCols(); i++) {
                assertTrue(t.get(j, i).equals(m.get(i, j)));
            }
        }
    }

    @Test
    public void TransposeColumnEqualsRow(){
        Matrix t = m.transpose();

        for (int j = 0; j < t.getCols(); j++) {

            for (int i = 0; i < t.getRows(); i++) {
                assertTrue(t.get(i, j).equals(m.get(j, i)));
            }
        }
    }
}
