import org.kevoree.ContainerNode;
import org.kevoree.ContainerRoot;
import org.kevoree.accesscontrol.*;
import org.kevoree.accesscontrol.compare.DefaultModelCompare;
import org.kevoree.accesscontrol.impl.DefaultAccessControlFactory;
import org.kevoree.accesscontrol.pep.PEP;


import org.kevoree.cloner.DefaultModelCloner;
import org.kevoree.impl.DefaultKevoreeFactory;
import org.kevoree.modeling.api.ModelCloner;
import org.kevoree.modeling.api.compare.ModelCompare;
import org.kevoree.modeling.api.trace.ModelTrace;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 7/10/13
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class TESTPEP {

    public static void main(String[] args) {
    //TODO
        AccessControlFactory factory = new DefaultAccessControlFactory();
        AccessControlPolicy modelAC = factory.createAccessControlPolicy();
        Domain myDomain = factory.createDomain();
        myDomain.setName("IaaSDomain");
        modelAC.addDomains(myDomain);
        Rule rule = factory.createRule();
        rule.setName("1");

        Subject subject = factory.createSubject();
        rule.setSubject(subject);


        Condition condition = factory.createCondition();
        rule.setCondition(condition);

        Action action = factory.createAction();
        rule.setAction(action);




        List<Resource> listresourcesource = (List<Resource>) factory.createResource();
        List<Resource> listresourcetarget = (List<Resource>) factory.createResource();
        rule.setSource(listresourcesource);
        rule.setTarget(listresourcetarget);











        PEP pep=new PEP();

        DefaultKevoreeFactory kevoreefactory = new DefaultKevoreeFactory();
        ModelCompare compare = new org.kevoree.compare.DefaultModelCompare();
        ModelCloner cloner = new DefaultModelCloner();

        ContainerRoot model = kevoreefactory.createContainerRoot();
        ContainerNode node1 = kevoreefactory.createContainerNode();
        node1.setName("node1");
        model.addNodes(node1);

        ContainerNode node2 = kevoreefactory.createContainerNode();
        node2.setName("node2");
        model.addNodes(node2);

        ContainerRoot model2 = cloner.clone(model);
        List<ModelTrace> traces = compare.diff(model, model2).getTraces();
        assert(traces.size() == 0);

        ContainerNode node3 = kevoreefactory.createContainerNode();
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
        String finaldecision = pep.PDP(traces, modelAC);
        System.out.println(finaldecision) ;





    }




}
