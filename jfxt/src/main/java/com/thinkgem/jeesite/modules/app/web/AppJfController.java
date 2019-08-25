package com.thinkgem.jeesite.modules.app.web;



import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.filemanage.FileUpload;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.app.util.AppResult;
import com.thinkgem.jeesite.modules.merchant.entity.*;
import com.thinkgem.jeesite.modules.merchant.service.*;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.DictService;
import com.thinkgem.jeesite.modules.sys.service.OfficeService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * @author App管理
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018-05-2815:49
 */
@Controller
@RequestMapping(value = "/app")
public class AppJfController {
    @Autowired
    private JfXjggService jfXjggService;
    @Autowired
    private JfXxService jfXxService;
    @Autowired
    private JfXjgcService jfXjgcService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private JfCfService jfCfService;
    @Autowired
    private DictService dictService;
    @Autowired
    private JfZgService jfZgService;
    @Autowired
	private OfficeService officeService;
    /**
     * 保存整改单过程
     *
     * @param
     * @return
     */
    @RequestMapping("/saveZgd")
    @ResponseBody
    public Object saveZgd(HttpServletResponse response,JfZg jfZg){
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            User user = null;
            if(StringUtils.isNotBlank(jfZg.getUserId())){
                user = systemService.getUser(jfZg.getUserId());
                jfZg.setCreateBy(user);
            }else {
                return AppResult.writeResultRep(null,"未登录或登录失效");
            }

            if (jfZg.getId()==null|| jfZg.getId()==""){//新整改单
                String tzd=""+new Date().getTime();
                jfZg.setZgdh(tzd);//生成新的单号
            }
            JfXjgc jfXjgc = new JfXjgc();
            if (jfZg.getKzzd1()!=null && !"newzgd".equals(jfZg.getKzzd1())){
                String xjId=jfZg.getKzzd1();
                //查询巡检单进行状态修改以及照片添加
                jfXjgc=jfXjgcService.get(xjId);
            }
                if(jfZg.getCfxczp() !=null) {
                	jfXjgc.setXczp(jfZg.getCfxczp());//照片
                }
                //2019-05-16 chelly add  整改同时开具处罚单 则整改单与巡检结果暂时不生效
                if("cfd".equals(jfZg.getKzzd2())){
                    jfZg.setDelFlag("1");
                }else{
                    jfXjgc.setDelFlag("0");
                }

                jfXjgcService.save(jfXjgc);
           
            //保存整改单
            jfZgService.save(jfZg);
            return AppResult.writeResultRep(jfZg,"保存整改单成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("保存整改单失败");
        }
    }


    /**
     * 保存处罚单过程
     *
     * @param
     * @return
     */
    @RequestMapping("/saveCfd")
    @ResponseBody
    public Object saveCfd(HttpServletResponse response,JfCf jfCf){
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            User user = null;
            if(StringUtils.isNotBlank(jfCf.getUserId())){
                user = systemService.getUser(jfCf.getUserId());
                jfCf.setCreateBy(user);
            }else {
                return AppResult.writeResultRep(null,"未登录或登录失效");
            }

            if (jfCf.getId()==null|| jfCf.getId()==""){//新处罚单
                String tzd=""+new Date().getTime();
                jfCf.setCftzd(tzd);//生成新的单号
            }

            //2019-05-16 chelly add 保存处罚单时判断是否从整改单过来，并更改巡检结果及整改单的有效状态
            if(jfCf.getKzzd1()!=null && !"".equals(jfCf.getKzzd1())){
                String xjId = jfCf.getKzzd1();
                JfXjgc jfXjgc=jfXjgcService.get(xjId);
                jfXjgc.setDelFlag("0");
                jfXjgcService.save(jfXjgc);
            }
            if(jfCf.getKzzd2()!=null && !"".equals(jfCf.getKzzd2())){
                String zgId = jfCf.getKzzd2();
                JfZg jfZg = jfZgService.get(zgId);
                jfCf.setCfxczp(jfZg.getCfxczp());
                jfZg.setDelFlag("0");
                jfZgService.save(jfZg);
            }

            jfCfService.save(jfCf);
            return AppResult.writeResultRep(jfCf,"保存处罚单成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("保存处罚单失败");
        }
    }



    /**
     * 字典列表
     *
     * @param
     * @return
     */
    @RequestMapping("/cfZdList")
    @ResponseBody
    public Object cfZdList(HttpServletResponse response,Dict dict) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
                List<Dict> dicts=dictService.findList(dict);
                return AppResult.writeResultRep(dicts, "获取字典列表成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取字典列表失败");
        }
    }



    /**
               *  网元列表
     *
     * @param
     * @return
     */
    @RequestMapping("/jfList")
    @ResponseBody
    public Object jfList(HttpServletResponse response, JfXx jfXx) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            List<JfXx> jfXxs = jfXxService.findList(jfXx);
            return AppResult.writeResultRep(jfXxs, "获取网元列表成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取网元列表失败");
        }
    }
    
    /**
              * 根据登录userId称获取所属区域的网元列表
     *
     * @param
     * @return
     */
    @RequestMapping("/jfListByUserId")
    @ResponseBody
    public Object jfListByLoginName(HttpServletResponse response,String userId) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
        	User appUser = systemService.getUser(userId);
        	String loginName = appUser.getLoginName();
        	List<Office> list = officeService.findByLoginName(loginName);
        	JfXx jfXx = new JfXx();
    		if (!list.isEmpty()) {
    			String jfjjName = list.get(0).getName();
    			if(jfjjName.contains(UserUtils.NETWORK_OPERATIONS_BRANCH)) {
    				jfXx.setUserId(userId);
    				}
    			}
        	List<JfXx> jfXxs = jfXxService.findList(jfXx);
            return AppResult.writeResultRep(jfXxs, "获取网元列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResult.writeResultFailure("返回公告详情失败");
        }
    }

    /**
     * 整改单详情
     *
     * @param
     * @return
     */
    @RequestMapping("/zgDetail")
    @ResponseBody
    public Object zgDetail(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            JfZg zgDetail = jfZgService.get(id);
            return AppResult.writeResultRep(zgDetail, "返回处罚单详情成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("返回处罚单详情失败");
        }
    }

    /**
     * 处罚单详情
     *
     * @param
     * @return
     */
    @RequestMapping("/cfDetail")
    @ResponseBody
    public Object cfDetail(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            JfCf cfDetail = jfCfService.get(id);
            return AppResult.writeResultRep(cfDetail, "返回处罚单详情成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("返回处罚单详情失败");
        }
    }
    /**
     * 处罚单列表
     *
     * @param
     * @return
     */
    @RequestMapping("/cfList")
    @ResponseBody
    public Object cfList(HttpServletResponse response,HttpServletRequest request , JfCf jfCf) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            if(jfCf!=null && StringUtils.isNotBlank(jfCf.getUserId())){
                User user = systemService.getUser(jfCf.getUserId());
                jfCf.setCurrentUser(user);
            }
            Page<JfCf> page = jfCfService.findPage(new Page<JfCf>(request, response), jfCf);
            List<JfCf> jfCfs = page.getList();
            return AppResult.writeResultRep(jfCfs, "获取处罚单列表失败");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取处罚单列表失败");
        }
    }
    
    /**
     * 巡检过程单详情
     *
     * @param
     * @return
     */
    @RequestMapping("/xjgcDetail")
    @ResponseBody
    public Object xjgcDetail(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
        	JfXjgc xjgcDetail = jfXjgcService.get(id);
            return AppResult.writeResultRep(xjgcDetail, "返回处罚单详情成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("返回处罚单详情失败");
        }
    }
    /**
     * 巡检过程列表
     *
     * @param
     * @return
     */
    @RequestMapping("/xjgcList")
    @ResponseBody
    public Object xjgcList(HttpServletResponse response,HttpServletRequest request , JfXjgc jfxjgc) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            if(jfxjgc!=null && StringUtils.isNotBlank(jfxjgc.getUserId())){
                User user = systemService.getUser(jfxjgc.getUserId());
                jfxjgc.setCurrentUser(user);
            }
            Page<JfXjgc> page = jfXjgcService.findPage(new Page<JfXjgc>(request, response), jfxjgc);
            List<JfXjgc> jfxjgcs = page.getList();
            return AppResult.writeResultRep(jfxjgcs, "获取巡检过程列表失败");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取巡检过程列表失败");
        }
    }
    
    /**
     * 整改单列表
     *
     * @param
     * @return
     */
    @RequestMapping("/zgList")
    @ResponseBody
    public Object zgList(HttpServletResponse response,HttpServletRequest request , JfZg jfZg) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            if(jfZg!=null && StringUtils.isNotBlank(jfZg.getUserId())){
                User user = systemService.getUser(jfZg.getUserId());
                jfZg.setCurrentUser(user);
            }
            List<JfZg> jfZgs = jfZgService.findList(jfZg);
            return AppResult.writeResultRep(jfZgs, "获取整改单列表失败");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取整改单列表失败");
        }
    }

    /**
     * 保存巡检过程
     *
     * @param
     * @return
     */
    @RequestMapping("/saveXj")
    @ResponseBody
    public Object saveXj(HttpServletResponse response,JfXjgc jfXjgc){
              response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            User user = null;
            if(StringUtils.isNotBlank(jfXjgc.getUserId())){
                user = systemService.getUser(jfXjgc.getUserId());
                jfXjgc.setCreateBy(user);
            }else {
                return AppResult.writeResultRep(null,"未登录或登录失效");
            }

            if (jfXjgc.getXjsftg().equals("0")){
                //说明是需要整改，先判断为无效，整改单保存后修改有效，存储照片
                jfXjgc.setDelFlag("1");
            }else if (jfXjgc.getXjsftg().equals("1")){
                //不需要整改，直接保存巡检单
                jfXjgc.setDelFlag("0");
            }

            jfXjgcService.save(jfXjgc);
            return AppResult.writeResultRep(jfXjgc,"保存巡检过程成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("保存巡检过程失败");
        }
    }

    /**
     * 通过公告ID查询网元信息
     *
     * @param
     * @return
     */
    @RequestMapping("/findJfbygg")
    @ResponseBody
    public Object findJfbygg(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
             //通过公告ID查询网元信息
             JfXjgg jfXjg = jfXjggService.get(id);
             JfXx JfXx = jfXxService.get(jfXjg.getJfid());
             return AppResult.writeResultRep(JfXx, "获取网元信息成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取网元信息失败");
        }
    }

    /**
     * 通过巡检ID查询网元信息
     *
     * @param
     * @return
     */
    @RequestMapping("/findJfInfo")
    @ResponseBody
    public Object findJfInfo(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
             //通过巡检ID查询网元信息
             JfXjgc jfXjc = jfXjgcService.get(id);
             JfXx JfXx = jfXxService.get(jfXjc.getXjjf());
             return AppResult.writeResultRep(JfXx, "获取网元信息成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取网元信息失败");
        }
    }
    
    /**
     * 公告列表
     *
     * @param
     * @return
     */
    @RequestMapping("/ggList")
    @ResponseBody
    public Object ggList(HttpServletResponse response,JfXjgg jfXjgg) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            List<JfXjgg> jfXjggs = jfXjggService.findList(jfXjgg);
            return AppResult.writeResultRep(jfXjggs, "获取公告列表成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取公告列表失败");
        }
    }

    /**
     * 最新公告查询
     *
     * @param
     * @return
     */
    @RequestMapping("/newggList")
    @ResponseBody
    public Object newggList(HttpServletResponse response,JfXjgg jfXjgg) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        jfXjgg.setFbrq(new Date());
        try {
            List<JfXjgg> jfXjggs = jfXjggService.findList(jfXjgg);
            return AppResult.writeResultRep(jfXjggs, "获取公告列表成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("获取公告列表失败");
        }
    }

    /**
     * 查询用户
     *
     * @param
     * @return
     */
    @RequestMapping("/findUser")
    @ResponseBody
    public Object findUser(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            User appUser = systemService.getUser(id);
            return AppResult.writeResultRep(appUser, "返回公告详情成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResult.writeResultFailure("返回公告详情失败");
        }
    }


    /**
     * 公告详情
     *
     * @param
     * @return
     */
    @RequestMapping("/ggDetail")
    @ResponseBody
    public Object ggDetail(HttpServletResponse response,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            JfXjgg ggDetail = jfXjggService.get(id);
            return AppResult.writeResultRep(ggDetail, "返回公告详情成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("返回公告详情失败");
        }
    }

    /**
     * 登录
     *
     * @param loginName  password
     * @return
     */
    @RequestMapping("/toLogin")
    @ResponseBody
    public Object toLogin(HttpServletResponse response,HttpServletResponse request,User user,String loginName, String password) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            user.setLoginName(loginName);
            user.setMobilePassword(password);
            User user1 = systemService.findUserApp(user);
            if(null == user1){
                return AppResult.writeResultFailure("用户名或密码不正确！");
            }
//            return AppResult.writeResultRep(user1,"登录成功");
            return this.getPermissions(response,request,user1.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return AppResult.writeResultFailure("登录失败");
        }
    }


    /**
     * 获取用户权限
     * @param response
     * @param request
     * @param id
     * @return
     */
    @RequestMapping("/getPermissions")
    @ResponseBody
    public Object getPermissions(HttpServletResponse response,HttpServletResponse request,String id) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            User user1 = new User();
            user1.setId(id);
            List<User> list = systemService.findUserPermissions(user1);
            String permissions="";

            for(User u:list){
                if(u!=null && StringUtils.isNotBlank(u.getPermissions())){
                    permissions+=u.getPermissions()+",";
                }
            }
            if(permissions.length()>1){
                permissions = permissions.substring(0,permissions.length()-1);
            }
            user1.setPermissions(permissions);
            return AppResult.writeResultRep(user1,"获取权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResult.writeResultFailure("获取权限失败");
        }
    }




    /**
     * 图片文件上传
     *
     * @param request
     * @param imgFile
     * @return
     */
    @RequestMapping(value = "/uploadImgFile", method = RequestMethod.POST, consumes = "multipart/form-data")
    @ResponseBody
    public Object uploadImgFile(HttpServletRequest request,HttpServletResponse response,@RequestParam("imgFile") MultipartFile imgFile) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            String filePath  ="/userfiles/1/images/app/zqsj";
            filePath = FileUpload.upload(request, filePath, imgFile);
            System.out.println(filePath);
            return AppResult.writeResultRep(filePath, "文件上传成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("文件上传失败");
        }
    }

    /**
     * 删除上传图片
     *
     * @param request
     * @param imgUrls
     * @return
     */
    @RequestMapping(value = "/deleteImg")
    @ResponseBody
    public Object deleteImg(HttpServletRequest request,HttpServletResponse response, String imgUrls) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            String[] imgArr = imgUrls.split(",");
            for (int i = 0; i < imgArr.length; i++) {

                String imgUrl = imgArr[i].replace("/crossfire", "");
                String path = request.getSession().getServletContext().getRealPath(imgUrl);
                File file = new File(path);
                file.delete();
            }

            return AppResult.writeResultRep("", "图片删除成功");
        } catch (Exception e) {
            return AppResult.writeResultFailure("图片删除失败");
        }
    }
    /**
     * 图片上传
     * @param response
     * @param imgData
     * @return
     */
    @RequestMapping("/uploadImg")
    @ResponseBody
    public Object uploadImg(HttpServletResponse response,String imgData){

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            //String destPath = request.getSession().getServletContext().getRealPath("");
            String destPath="";
            String returnPath="";
            returnPath = "/userfiles/1/_thumbs/images/app/xjxcimg/";
            destPath = Global.getUserfilesBaseDir() + returnPath;
            File dirFile = new File(destPath);
            if (!dirFile.exists()) {    //创建文件夹
                dirFile.mkdirs();
            }
            //生成jpeg图片
            Date date = new Date();
            //SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
            //String dateString = formatter.format(formatter);
            String dateString = String.valueOf(date.getTime());
            String imgFilePath = destPath+dateString+".jpg";//新生成的图片
            GenerateImage(imgData,imgFilePath);
            String a = returnPath+dateString+".jpg";
            return AppResult.writeResultRep(a, "上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return AppResult.writeResultFailure("上传失败");
        }
    }

    /**
     * 将64位生成新的图片
     * @param imgData
     * @param destPath
     * @return
     */
    public static boolean GenerateImage(String imgData,String destPath)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgData == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgData);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(destPath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


}
