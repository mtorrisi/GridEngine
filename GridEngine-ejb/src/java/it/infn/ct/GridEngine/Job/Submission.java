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
    public void submitJobASync(InfrastructureInfo infra, GEJobDescription jobDescription, String commonName, String tcpAddress, int interactionId, String userDescription, String userEmail, String DB, String DBUser, String DbUserPwd, boolean randomCE, boolean checkJobStatus) {
        InfrastructureInfo infras[] = new InfrastructureInfo[1];

        MultiInfrastructureSubmission mijs = null;
        if (!DB.equals("") && !DBUser.equals("") && !DbUserPwd.equals("")) {
            _log.info("Using local DB connection");
            mijs = new MultiInfrastructureSubmission(DB, DBUser, DbUserPwd);
        } else {
            _log.info("Using connection Pool");
            mijs = new MultiInfrastructureSubmission();
        }

        mijs.setArguments(jobDescription.getArguments());

        mijs.setExecutable(jobDescription.getExecutable());
        mijs.setInputFiles(jobDescription.getInputFiles());
        if (!jobDescription.getJDLRequirements().equals("")) {
            mijs.setJDLRequirements(jobDescription.getJDLRequirements().split(","));
        }

        mijs.setJobError(jobDescription.getError());
        mijs.setJobOutput(jobDescription.getOutput());
        mijs.setJobQueue(jobDescription.getQueue());
        mijs.setNumberOfProcesses(jobDescription.getNumberOfProcesses());
        mijs.setOutputFiles(jobDescription.getOutputFiles());
        mijs.setOutputPath(jobDescription.getOutputPath());
        mijs.setSPMDVariation(jobDescription.getSPDMVariation());
        mijs.setTotalCPUCount(jobDescription.getTotalCPUCount());
        mijs.setUserEmail(userEmail);

        if (randomCE) {
            mijs.setRandomCE(randomCE);
        }

        if (!checkJobStatus) {
            mijs.setCheckJobsStatus(checkJobStatus);
        }

        _log.debug("Submitting an async job");
        mijs.submitJobAsync(infra, commonName, tcpAddress, interactionId, userDescription);
        _log.debug("Job submitted");
    }
//
//    @Override
//    public void setUserEmail(String userEmail) {
//        this.userEmail = userEmail;
//    }
//
//    @Override
//    public void setRandomCE(boolean randomCE) {
//        this.randomCE = randomCE;
//    }
//
//    @Override
//    public void setDB(String db) {
//        this.DB = db;
//    }
//
//    @Override
//    public void setDbName(String dbUserName) {
//        this.DBUser = dbUserName;
//    }
//
//    @Override
//    public void setDbPasswd(String dbUserPasswd) {
//        this.DBUserPwd = dbUserPasswd;
//    }

}
