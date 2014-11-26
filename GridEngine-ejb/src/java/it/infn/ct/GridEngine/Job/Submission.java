/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.infn.ct.GridEngine.Job;

import it.infn.ct.GridEngine.JobResubmission.GEJobDescription;
import javax.ejb.Stateless;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Marco Fargetta <marco.fargetta@ct.infn.it>
 */
@Stateless
public class Submission implements SubmissionRemote {

    Log _log = LogFactory.getLog(Submission.class);

//    @Override
//    public String submitJob(it.infn.ct.GridEngine.Job.InfrastructureInfo infra, it.infn.ct.GridEngine.JobResubmission.GEJobDescription jobDescription, String commonName, String tcpAddress, int interactionId, String userDescription) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public void submitJobASync(Wrapper wrapper) {

        MultiInfrastructureSubmission mijs = null;
        if (!wrapper.getDB().equals("") && !wrapper.getDBUser().equals("") && !wrapper.getDbUserPwd().equals("")) {
            _log.info("Using local DB connection");
            mijs = new MultiInfrastructureSubmission(wrapper.getDB(), wrapper.getDBUser(), wrapper.getDbUserPwd());
        } else {
            _log.info("Using connection Pool");
            mijs = new MultiInfrastructureSubmission();
        }

        mijs.setArguments(wrapper.getJobDescription().getArguments());

        mijs.setExecutable(wrapper.getJobDescription().getExecutable());
        mijs.setInputFiles(wrapper.getJobDescription().getInputFiles());
        if (!wrapper.getJobDescription().getJDLRequirements().equals("")) {
            mijs.setJDLRequirements(wrapper.getJobDescription().getJDLRequirements().split(","));
        }

        mijs.setJobError(wrapper.getJobDescription().getError());
        mijs.setJobOutput(wrapper.getJobDescription().getOutput());
        mijs.setJobQueue(wrapper.getJobDescription().getQueue());
        mijs.setNumberOfProcesses(wrapper.getJobDescription().getNumberOfProcesses());
        mijs.setOutputFiles(wrapper.getJobDescription().getOutputFiles());
        mijs.setOutputPath(wrapper.getJobDescription().getOutputPath());
        mijs.setSPMDVariation(wrapper.getJobDescription().getSPDMVariation());
        mijs.setTotalCPUCount(wrapper.getJobDescription().getTotalCPUCount());
        mijs.setUserEmail((wrapper.getUserEmail() != null) ? wrapper.getUserEmail() : "");
        mijs.setRandomCE(wrapper.isRandomCE());
        mijs.setCheckJobsStatus(true);
//        if (!wrapper.isCheckJobStatus()) {
//
//        }

        _log.debug("Submitting an async job");
        mijs.submitJobAsync(wrapper.getInfra(), wrapper.getCommonName(), wrapper.getTcpAddress(), wrapper.getInteractionId(), wrapper.getUserDescription());
        _log.debug("Job submitted");
    }

}
