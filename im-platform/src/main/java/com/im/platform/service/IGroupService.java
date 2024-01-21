package com.im.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.im.platform.entity.Group;
import com.im.platform.vo.GroupInviteVO;
import com.im.platform.vo.GroupMemberVO;
import com.im.platform.vo.GroupVO;

import java.util.List;


public interface IGroupService extends IService<Group> {


    GroupVO createGroup(String groupName);

    GroupVO modifyGroup(GroupVO vo);

    void deleteGroup(Long groupId);

    void quitGroup(Long groupId);

    void kickGroup(Long groupId,Long userId);

    List<GroupVO>  findGroups();

    void invite(GroupInviteVO vo);

    Group GetById(Long groupId);

    GroupVO findById(Long groupId);

    List<GroupMemberVO> findGroupMembers(Long groupId);
}
