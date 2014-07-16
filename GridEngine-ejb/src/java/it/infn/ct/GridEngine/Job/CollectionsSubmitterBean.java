/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.infn.ct.GridEngine.Job;

import it.infn.ct.GridEngine.JobCollection.CollectionsSubmitterBeanRemote;
import it.infn.ct.GridEngine.JobCollection.JobCollection;
import it.infn.ct.GridEngine.JobCollection._JobCollection;
import it.infn.ct.GridEngine.JobCollection._JobCollectionSubmission;
import it.infn.ct.GridEngine.JobResubmission.GEJobDescription;
import it.infn.ct.GridEngine.JobResubmission._GEJobDescription;
import java.util.ArrayList;
import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mario
 */
@Stateless
public class CollectionsSubmitterBean implements CollectionsSubmitterBeanRemote {

    Log _log = LogFactory.getLog(CollectionsSubmitterBean.class);
    
    @Override
    public void submitJobCollection(InfrastructureInfo[] infrastructures, String tcpAddress, int gridInteractionId, JobCollection jobCollection,String DB, String DBUser, String DBUserPwd) {
        ArrayList<_GEJobDescription> geDescriptions = new ArrayList<_GEJobDescription>();
        for (GEJobDescription description : jobCollection.getSubJobDescriptions()) {
            _GEJobDescription geDescription = new _GEJobDescription(description.getExecutable(), 
                                                                    description.getArguments(), 
                                                                    description.getOutput(), 
                                                                    description.getError(), 
                                                                    description.getQueue(), 
                                                                    description.getFileTransfer(), 
                                                                    description.getTotalCPUCount(), 
                                                                    description.getSPDMVariation(), 
                                                                    description.getNumberOfProcesses(), 
                                                                    (!description.getJDLRequirements().equals("")) ? description.getJDLRequirements() : null, 
                                                                    description.getOutputPath(), 
                                                                    description.getInputFiles(), 
                                                                    description.getOutputFiles(), 
                                                                    description.isProxyRenewal());
            geDescriptions.add(geDescription);
        }
        _JobCollectionSubmission jobCollectionSubmission = null;
        _JobCollection geJobCollection = new _JobCollection(jobCollection.getCommonName(), jobCollection.getDescription(), jobCollection.getOutputPath(), geDescriptions);        
        if(DB != null && DBUser != null && DBUserPwd != null){
           _log.info("Submittin Collection with local DB conn parameters...");
            //_JobCollection jobCollection = new _JobCollection(common, DBUserPwd, DBUserPwd, null), DBUserPwd, DBUserPwd, null)
           jobCollectionSubmission = new _JobCollectionSubmission(DB, DBUser, DBUserPwd, geJobCollection);
        } else {
            _log.info("Submittin Collection with glassfish conn pool...");
            jobCollectionSubmission = new _JobCollectionSubmission(geJobCollection);
        }
        
        jobCollectionSubmission.submitJobCollection(infrastructures, tcpAddress, gridInteractionId);
    }

   
}
