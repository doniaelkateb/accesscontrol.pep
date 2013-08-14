package org.kevoree.accesscontrol.pep;
import org.kevoree.accesscontrol.*;
import org.kevoree.accesscontrol.impl.DefaultAccessControlFactory;
import org.kevoree.accesscontrol.compare.DefaultModelCompare;
import org.kevoree.modeling.api.compare.ModelCompare;
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: donia.elkateb
 * Date: 7/9/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class PEPMain {


    public static void main(String[] args) {
        //TODO
        AccessControlFactory factory = new DefaultAccessControlFactory();
        AccessControlPolicy modelAC = factory.createAccessControlPolicy();
        Domain myDomain = factory.createDomain();
        myDomain.setName("IaaSDomain");
        modelAC.addDomains(myDomain);
        Rule rule = factory.createRule();
        rule.setName("rule32");

    }


    public void createrule(String rulename, Subject subject, Action action, Condition condition, List<Resource> resources_source,  List<Resource>  resouces_target, AccessControlPolicy acModel)
    {
                 AccessControlFactory factory = new DefaultAccessControlFactory();
                 Rule rule = factory.createRule();
                 rule.setName(rulename);
                 rule.setSubject(subject);
                 rule.setCondition(condition);
                 rule.setAction(action);
                 rule.setSource(resources_source);
                 rule.setTarget(resouces_target);
    }

    //Get rules for a gib
    public boolean check(String id, String action, AccessControlPolicy acModel) {
        List<Rule> collectedRules = new ArrayList<Rule>();
        //locate rules from an ID
        Entity entity = acModel.findEntitiesByID(id);
        for (Rule rule : acModel.getRules()) {
            if (rule.getSubject().equals(entity)) {
                collectedRules.add(rule);
            } else {
                for (Role role : entity.getRoles()) {
                    if (rule.getSubject().equals(role)) {
                        collectedRules.add(rule);

                    }
                }
            }
        }
        return true;
    }

    public String PDP(String subject, String action, String resources_source, String resources_target, AccessControlPolicy acModel) {


        //locate rules from an ID
        String  nonapplicable = "no rule is applicable for this evaluation";
        String  deny = "deny";
        String  permit = "permit";
        String  postpone = "postpone";
        List<Rule> collectedRules = new ArrayList<Rule>();

        acModel.getDomains();
        for (Domain domain : acModel.getDomains())
        {
            collectedRules = domain.getRules();
        }

        for (Rule rule : collectedRules)
        {
            List list = new ArrayList();
            if ((rule.getAction().equals(action)) && (rule.getSource().equals(resources_source))  &&  (rule.getTarget().equals(resources_target)) )
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





    public void delegate(String id,String delegator,String delegatee,AccessControlPolicy acModel)
         //add rules to entity or role
    {
        List<Rule> collectedRules = new ArrayList<Rule>();
        //locate rules from an ID
        Entity entity = acModel.findEntitiesByID(id);
        for (Rule rule : acModel.getRules())
        {
            if (rule.getSubject().equals(delegator))
            {
                collectedRules.add(rule);
            }
       }
        //Entity entity = acModel.findEntitiesByID(id);
       // delegatee.add(collectedRules);
    }




/*      No order between roles
    public Subject conflictresolution(Entity subject, Entity subject1, Domain domain, TimingCondition condition, AccessControlPolicy acModel)
{

      if (subject.getRoles() >= subject1.getRoles())
      {
          return subject;

      else

          return subject1;
      }

      return subject;


}       */


   }