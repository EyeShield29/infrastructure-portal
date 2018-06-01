package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.ibatis.javassist.expr.NewArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.common.Sysconfig;
import com.infrastructure.portal.entity.po.message.MessageInfo;
import com.infrastructure.portal.entity.po.portal.PortalRole;
import com.infrastructure.portal.entity.po.portal.PortalUser;
import com.infrastructure.portal.entity.po.project.ProjectType;
import com.infrastructure.portal.entity.vo.QueryDocumentInfoVo;
import com.infrastructure.portal.entity.vo.QueryPortalRoleVo;
import com.infrastructure.portal.entity.vo.QueryProjectVo;
import com.infrastructure.portal.entity.vo.QueryUserVo;
import com.infrastructure.portal.entity.vo.QueryWorkVo;
import com.infrastructure.portal.mapper.common.SysconfigMapper;
import com.infrastructure.portal.mapper.document.DocumentInfoMapper;
import com.infrastructure.portal.mapper.message.MessageInfoMapper;
import com.infrastructure.portal.mapper.portal.PortalRoleMapper;
import com.infrastructure.portal.mapper.project.ProjectMapper;
import com.infrastructure.portal.mapper.user.PortalUserMapper;
import com.infrastructure.portal.service.document.DocumentInfoService;
import com.infrastructure.portal.service.message.MessageInfoService;
import com.infrastructure.portal.service.portal.PortalRoleService;
import com.infrastructure.portal.service.project.ProjectService;
import com.infrastructure.portal.service.user.PortalUserService;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-core.xml")
public class TestAll{
	@Autowired
	private SysconfigMapper sysconfigMapper;
	@Test
	public void test() {
		/*int target=7;
		int array[][] = {{}};
		System.out.println(array.length);
		System.out.println(Find(target, array));*/
		int []nums = new int[] {1,2,2,3,1};
		System.out.println(findShortestSubArray(nums));
	}
	/*
		在一个二维数组中，每一行都按照从左到右递增的顺序排序，
		每一列都按照从上到下递增的顺序排序。请完成一个函数，
		输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
    }*/
	public static int findShortestSubArray(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int maxfre = 0;
        int minlen = Integer.MAX_VALUE;
        int start = 0;
        int end = nums.length - 1;
        List<Integer> list = new ArrayList<Integer>();
        for(int i : nums){
            if(!map.containsKey(i)){
                map.put(i,1);
            }else{
                int count = map.get(i);
                map.put(i, ++count);
            }
        }
        for(Integer i : map.keySet()) {
        	maxfre = Math.max(maxfre, map.get(i));
        }
        for(Integer i : map.keySet()){
            if(map.get(i) == maxfre){
                list.add(i);
            }
        }
        for(int i : list){
        	while(nums[start] != i || nums[end] != i){
                if(nums[start] != i){
                    start++;
                }
                if(nums[end] != i){
                    end--;
                }
            }
            minlen = Math.min(minlen, end - start + 1);
            start = 0;
            end = nums.length - 1;
        }
        return minlen;
    }
	public boolean Find(int target, int [][] array) {
		int i=0,flag=1;
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		if(array.length !=0 && array[0].length != 0) {
			for(;i<array.length;i++) {
				if(target>array[i][array[i].length-1]) {
					flag = 0;
					break;
				}
			}
			if(flag == 1) {
				i=0;
			}
			for(;i<array.length;i++) {
				for(int j=0;j<array[i].length;j++) {
					if(target == array[i][j]) {
						return true;
					}
				}
			}
		}
		return false;
    }
	
}
