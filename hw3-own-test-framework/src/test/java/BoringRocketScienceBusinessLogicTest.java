import ru.otus.hw3.annotations.*;
import ru.otus.hw3.assertions.Assertions;

public class BoringRocketScienceBusinessLogicTest {

    int[] dataset;
    Network net;

    int testCount;

    @Before
    void initTestCount(){
        testCount = 0;
    }

    @Before
    void loadDataset(){
        dataset = new int[1000];
    }

    @Before
    void preprocessDataSet(){
        for (int i = 0; i < dataset.length; i++){
            dataset[i] = i;
        }
    }

    @Before
    void loadNetwork(){
        net = new Network();
    }

    @Test
    void trainNetworkInRightEnvironment(){
        testCount += 1;
        Assertions.assertTrue(net.trainOk());
    }

    @Test
    void trainNetworkBadEnvironment(){
        testCount += 1;
        Assertions.assertTrue(net.trainBad());
    }

    @LastTest
    void countMustBeTwo(){
        Assertions.assertTrue(testCount == 2);
    }

    @After
    void releaseDataset(){
        dataset = null;
    }

    @After
    void releaseNetworkResources(){
        net.release();
    }
}

class Network{
    public boolean trainOk(){
       return true;
    }

    public boolean trainBad(){
        return false;
    }

    public void release(){

    }
}
