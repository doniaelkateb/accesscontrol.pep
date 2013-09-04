package org.kevoree.accesscontrol.pep;

import org.kevoree.accesscontrol.*;
import org.kevoree.modeling.api.trace.ModelAddAllTrace;
import org.kevoree.modeling.api.trace.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 7/9/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */

public class PEP{

    String  nonapplicable = "no rule is applicable for this evaluation";
    String  deny = "deny";
    String  permit = "permit";
    String  postpone = "postpone";


    public String ReturnTraceEffect(String action, String resources_source, String resources_target, AccessControlPolicy acModel)
    {


        //explore all the rules in the policy
        List<Rule> collectedRules = new ArrayList<Rule>();
        acModel.getDomains();
        for (Domain domain : acModel.getDomains())
        {
            collectedRules = domain.getRules();
        }
        List list = new ArrayList();

        for (Rule rule : collectedRules)
        {

            if ((rule.getSource().equals(resources_source)) && (rule.getTarget().equals(resources_target))  && (rule.getAction().equals(action)) )
            {

                list.add(rule.getEffect().toString()) ;
            }

            while (list.iterator().hasNext())
            {
                if( list.iterator().next().equals("deny"))
                {
                    return deny;
                }
            }

            while (list.iterator().hasNext())
            {
                if( list.iterator().next().equals("postpone"))
                {
                    return postpone;
                }
            }

            while (list.iterator().hasNext())
            {
                if( list.iterator().next().equals("permit"))
                {
                    return permit;
                }
            }

        }
        return nonapplicable;
    }


    public String PDP(List<ModelTrace> traces, AccessControlPolicy acModel) {

        //explore all the input traces

        List<String> decisiontraces = new ArrayList<String>();

        for (int i=0; i < traces.size(); i++)
        {

            ModelTrace trace = traces.get(i);
            if (trace instanceof ModelSetTrace)
            {

                ModelSetTrace settrace   =  (ModelSetTrace)trace;
                String tempdecision  = ReturnTraceEffect("SetTrace", " ", settrace.getSrcPath(), acModel);
                //System.out.println(tempdecision);
                decisiontraces.add(tempdecision);
                //System.out.println(settrace.getSrcPath());
                //System.out.println(settrace.getRefName());
            }


            if (trace instanceof ModelAddTrace)
            {
                System.out.println("AddTrace");
                ModelAddTrace addtrace   =  (ModelAddTrace)trace;
                String tempdecision  = ReturnTraceEffect("AddTrace", addtrace.getRefName(), addtrace.getPreviousPath(), acModel);
                //System.out.println(tempdecision);
                decisiontraces.add(tempdecision);
               // System.out.println(addtrace.getRefName());
                //System.out.println(addtrace.getPreviousPath());
               // System.out.println(addtrace.getTypeName());
            }



            if (trace instanceof ModelRemoveTrace)
            {
                System.out.println("RemoveTrace");
                ModelRemoveTrace removetrace   =  (ModelRemoveTrace)trace;
                String tempdecision  = ReturnTraceEffect("AddTrace", removetrace.getObjPath(), removetrace.getSrcPath(), acModel);
                decisiontraces.add(tempdecision);
                //System.out.println(removetrace.getRefName());
                //System.out.println(removetrace.getObjPath());
                //System.out.println(removetrace.getSrcPath());
            }



            if (trace instanceof ModelRemoveAllTrace)
            {
                System.out.println("ModelRemoveAllTrace");
            }



            if (trace instanceof ModelAddAllTrace)
            {
                System.out.println("ModelAddAllTrace");
            }

        }



        while (decisiontraces.iterator().hasNext())
        {
            if( decisiontraces.iterator().next().equals("deny"))
            {
                //System.out.println(decisiontraces.iterator().toString());
                return deny;
            }
        }


        while (decisiontraces.iterator().hasNext())
        {
            if( decisiontraces.iterator().next().equals("postpone"))
            {
                //System.out.println(decisiontraces.iterator().toString());
                return postpone;
            }
        }


        while (decisiontraces.iterator().hasNext())
        {
            if( decisiontraces.iterator().next().equals("permit"))
            {
                //System.out.println(decisiontraces.iterator().toString());
                return permit;
            }
        }

        return nonapplicable;
    }


    }




