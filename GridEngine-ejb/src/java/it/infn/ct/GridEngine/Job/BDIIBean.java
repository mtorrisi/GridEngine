/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.infn.ct.GridEngine.Job;

import it.infn.ct.GridEngine.InformationSystem.BDIIBeanRemote;
import it.infn.ct.GridEngine.InformationSystem._BDII;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author mario
 */
@Stateless
public class BDIIBean implements BDIIBeanRemote {

    Log _log = LogFactory.getLog(BDIIBean.class);

    @Override
    public List<String> queryCEForSWTag(URI uri, String swTag) {
        _BDII bdii = new _BDII(uri);
        List<String> result = null;
        try {
            result = bdii.queryCEForSWTag(swTag);
        } catch (NamingException ex) {
            _log.error(BDIIBean.class.getName(), ex);
        }
        return result;
    }

    @Override
    public List<String> queryCEQueues(URI uri, String vo) {
        _BDII bdii = new _BDII(uri);
        List<String> result = null;
        try {
            result = bdii.queryCEQueues(vo);
        } catch (NamingException ex) {
            _log.error(BDIIBean.class.getName(), ex);
        }
        return result;
    }

    @Override
    public List<URI> queryWMSURIs(URI uri, String vo) {
        _BDII bdii = new _BDII(uri);
        List<URI> result = null;
        try {
            result = bdii.queryWMSURIs(vo);
        } catch (NamingException ex) {
            _log.error(BDIIBean.class.getName(), ex);
        }
        return result;
    }

    @Override
    public String getRandomWMS(URI location, String vo) {
        _BDII bdii = new _BDII(location);
        return bdii.getRandomWMS(vo);

    }

    @Override
    public String getRandomCE(URI location, String vo) {
        _BDII bdii = new _BDII(location);
        return bdii.getRandomCE(vo);
    }

    @Override
    public String[] getCECoordinate(URI location, String ce) {
        _BDII bdii = new _BDII(location);
        String[] result = null;
        try {
            String[] tmp = bdii.queryCECoordinate(ce);
            result = new String[tmp.length];
        } catch (NamingException ex) {
            Logger.getLogger(BDIIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String getRandomCEForSWTag(URI location, String tag) {
        _BDII bdii = new _BDII(location);
        String result = "";
        try {
            result = bdii.getRandomCEForSWTag(tag);
        } catch (NamingException ex) {
            Logger.getLogger(BDIIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<String> queryCEFromSWTag_MaxCPUTime(URI location, String TAG, String VO, Integer MaxCPUTime) {
        _BDII bdii = new _BDII(location);
        List<String> result = null;
        try {
            result = bdii.queryCEFromSWTag_MaxCPUTime(TAG, VO, MaxCPUTime);
        } catch (NamingException ex) {
            _log.error(BDIIBean.class.getName(), ex);
        }
        return result;
    }

    @Override
    public String getRandomCEFromSWTag_MaxCPUTime(URI location, String TAG, String VO, Integer MaxCPUTime) {
        _BDII bdii = new _BDII(location);
        String result = "";
        try {
            result = bdii.getRandomCEFromSWTag_MaxCPUTime(TAG, VO, MaxCPUTime);
        } catch (NamingException ex) {
            Logger.getLogger(BDIIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String getGlueSEImplementationName(URI location, String seHostname) {
        _BDII bdii = new _BDII(location);
        String result = "";
        try {
            result = bdii.getGlueSEImplementationName(seHostname);
        } catch (NamingException ex) {
            Logger.getLogger(BDIIBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
