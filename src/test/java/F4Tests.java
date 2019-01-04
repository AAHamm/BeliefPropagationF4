
import Structures.F4;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Åsmund Hammer on 25.04.18.
 */
public class F4Tests {

    @Test
    public void F4ExtensionOfF2Property(){
        assertEquals(F4.OMEGASQ.add(F4.OMEGA).add(F4.ONE), F4.ZERO);
        assertEquals(F4.OMEGASQ.add(F4.OMEGASQ), F4.ZERO);
    }

    @Test
    public void additionIdentityProperty(){
        ArrayList<F4> elements = F4.list();


        for (int i = 0; i < elements.size(); i++) {
            F4 a = elements.get(i);
            assertEquals(a, F4.ZERO.add(a));
        }
    }
    @Test
    public void multiplicationIdentityProperty(){
        ArrayList<F4> elements = F4.list();


        for (int i = 0; i < elements.size(); i++) {
            F4 a = elements.get(i);
            assertEquals(a, F4.ONE.multiply(a));
        }
    }

    @Test
    public void additiveCommutativeProperty(){
        ArrayList<F4> elements = F4.list();

        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < elements.size(); j++) {
                F4 a = elements.get(i);
                F4 b = elements.get(j);
                assertEquals(a.add(b), b.add(a));
            }
        }
    }

    @Test
    public void multiplicativeCommutativeProperty(){
        ArrayList<F4> elements = F4.list();

        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < elements.size(); j++) {
                F4 a = elements.get(i);
                F4 b = elements.get(j);
                assertEquals(a.multiply(b), b.multiply(a));
            }
        }
    }

    @Test
    public void F4ElementPersists(){
        F4 a = F4.OMEGA;
        F4 b = F4.OMEGA;
        a.add(b);

        assertEquals(a, F4.OMEGA);
    }
}
