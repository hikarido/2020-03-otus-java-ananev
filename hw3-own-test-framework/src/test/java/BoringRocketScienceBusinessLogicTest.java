import ru.otus.hw3.annotations.*;
import ru.otus.hw3.assertions.Assertions;

public class BoringRocketScienceBusinessLogicTest {

    int[] dataset;
    Network net;

    int testCount;

    @Before
    public void initTestCount(){
        testCount = 0;
    }

    @Before
    public void loadDataset(){
        dataset = new int[1000];
    }

    @Before
    public void preprocessDataSet(){
        for (int i = 0; i < dataset.length; i++){
            dataset[i] = i;
        }
    }

    @Before
    public void loadNetwork(){
        net = new Network();
    }

    @Test
    public void trainNetworkInRightEnvironment(){
        testCount += 1;
        Assertions.assertTrue(net.trainOk());
    }

    @Test
    public void trainNetworkBadEnvironment(){
        testCount += 1;
        Assertions.assertTrue(net.trainBad());
    }

    @LastTest
    public void countMustBeTwo(){
        Assertions.assertTrue(testCount == 2);
    }

    @After
    public void releaseDataset(){
        dataset = null;
    }

    @After
    public void releaseNetworkResources(){
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
