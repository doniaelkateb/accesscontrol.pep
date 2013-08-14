import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.compare.DefaultModelCompare;
import org.kevoree.cloner.DefaultModelCloner;
import org.kevoree.impl.DefaultKevoreeFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.api.trace.ModelAddAllTrace;
import org.kevoree.modeling.api.trace.*;

import java.util.List;

/**
 * Created by duke on 26/07/13.
 *
 *
 */

/*public val SET : Int = 0;
public val ADD : Int = 1;
public val REMOVE : Int = 2;
public val ADD_ALL : Int = 3;
public val REMOVE_ALL : Int = 4;
public val RENEW_INDEX : Int = 5; */


/* object ElementAttributeType{

public val ATTRIBUTE : Int = 0;
public val REFERENCE : Int = 1;
public val CONTAINMENT : Int = 2;
}*/

public class TraceDiffTest {

    public static void main(String[] args) {

        DefaultKevoreeFactory factory = new DefaultKevoreeFactory();

        ModelCompare compare = new DefaultModelCompare();
        ModelCloner cloner = new DefaultModelCloner();

        ContainerRoot model = factory.createContainerRoot();

        ContainerNode node1 = factory.createContainerNode();
        node1.setName("node1");
        model.addNodes(node1);

        ContainerNode node2 = factory.createContainerNode();
        node2.setName("node2");
        model.addNodes(node2);

        ContainerRoot model2 = cloner.clone(model);


        List<ModelTrace> traces = compare.diff(model, model2).getTraces();
        assert(traces.size() == 0);

       ContainerNode node3 = factory.createContainerNode();
       node3.setName("node3");
       model2.addNodes(node3);




      // model.removeNodes(node1);


      /*  List<Object> NodeList = model.selectByQuery("nodes[{ name = *node* }]");
        for (Object node: NodeList ) {
            model2.addNodes((ContainerNode)node);
        }


       List<Object> NodeList1 = model.selectByQuery("nodes[{ name = *node* }]");
        for (Object node: NodeList1 ) {
            model2.removeNodes((ContainerNode)node);
       }     */



        node1.setName("node10");
        traces = compare.diff(model2, model).getTraces();


        System.out.println(traces);
        for (int i=0; i < traces.size(); i++)
        {
        System.out.println("---------------------------------");
        ModelTrace trace = traces.get(i);
        System.out.println(trace.toString());



        if (trace instanceof ModelSetTrace)
        {
        System.out.println("SetTrace");
        ModelSetTrace settrace   =  (ModelSetTrace)trace;
        System.out.println(settrace.getSrcPath());
        System.out.println(settrace.getRefName());
        System.out.println(settrace.getObjPath());
        }


        if (trace instanceof ModelAddTrace)
        {
        System.out.println("AddTrace");
        ModelAddTrace addtrace   =  (ModelAddTrace)trace;
        System.out.println(addtrace.getRefName());
        System.out.println(addtrace.getPreviousPath());
        System.out.println(addtrace.getTypeName());
        }

        if (trace instanceof ModelAddAllTrace)
        {
        System.out.println("ModelAddAllTrace");
        }


        if (trace instanceof ModelRemoveAllTrace)
        {
        System.out.println("ModelRemoveAllTrace");
        }

        if (trace instanceof ModelRemoveTrace)
        {
        System.out.println("RemoveTrace");
        ModelRemoveTrace removetrace   =  (ModelRemoveTrace)trace;
        System.out.println(removetrace.getRefName());
        System.out.println(removetrace.getObjPath());
       // System.out.println(removetrace.getSrcPath());
        }



        }

    }

}