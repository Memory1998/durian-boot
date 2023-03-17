package com.breeze.boot.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.breeze.boot.quartz.domain.SysQuartzJob;
import com.breeze.boot.quartz.dto.JobDTO;
import com.breeze.boot.quartz.manager.QuartzManager;
import com.breeze.boot.quartz.mapper.SysQuartzJobMapper;
import com.breeze.boot.quartz.service.SysQuartzJobService;
import com.breeze.core.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

/**
 * Quartz任务调度Impl
 *
 * @author gaoweixuan
 */
@Service
public class SysQuartzJobServiceImpl extends ServiceImpl<SysQuartzJobMapper, SysQuartzJob> implements SysQuartzJobService {

    /**
     * quartz 管理器
     */
    @Autowired
    private QuartzManager quartzManager;

    /**
     * 负载任务
     */
    @PostConstruct
    private void loadJobs() {
        List<SysQuartzJob> quartzJobs = this.list();
        for (SysQuartzJob quartzJob : quartzJobs) {
            this.quartzManager.addOrUpdateJob(quartzJob);
        }
    }

    /**
     * 列表页面
     *
     * @param jobDTO 任务dto
     * @return {@link Page}<{@link SysQuartzJob}>
     */
    @Override
    public Page<SysQuartzJob> listPage(JobDTO jobDTO) {
        return this.baseMapper.listPage(new Page<>(jobDTO.getCurrent(), jobDTO.getSize()), jobDTO);
    }

    /**
     * 保存任务
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> saveJob(SysQuartzJob sysQuartzJob) {
        if (this.checkExists(sysQuartzJob)) {
            return Result.fail(Boolean.FALSE, "添加失败");
        }
        sysQuartzJob.insert();
        this.quartzManager.addOrUpdateJob(sysQuartzJob);
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 更新任务通过id
     *
     * @param sysQuartzJob quartz任务
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> updateJobById(SysQuartzJob sysQuartzJob) {
        if (this.checkExists(sysQuartzJob)) {
            return Result.fail(Boolean.FALSE, "修改失败");
        }
        sysQuartzJob.updateById();
        this.quartzManager.addOrUpdateJob(sysQuartzJob);
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 暂停任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> pauseJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        if (Objects.isNull(quartzJob)) {
            return Result.fail(Boolean.FALSE, "任务不存在");
        }
        quartzJob.setStatus(0);
        quartzJob.updateById();
        this.quartzManager.pauseJob(quartzJob.getJobName(), quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 恢复任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> resumeJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        if (Objects.isNull(quartzJob)) {
            return Result.fail(Boolean.FALSE, "任务不存在");
        }
        quartzJob.setStatus(1);
        quartzJob.updateById();
        this.quartzManager.resumeJob(quartzJob.getJobName(), quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 删除任务
     *
     * @param jobId 任务id
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> deleteJob(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        quartzJob.deleteById();
        this.quartzManager.deleteJob(quartzJob.getJobName(), quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 立刻运行
     *
     * @param jobId 任务ID
     * @return {@link Result}<{@link Boolean}>
     */
    @Override
    public Result<Boolean> runJobNow(Long jobId) {
        SysQuartzJob quartzJob = this.getById(jobId);
        this.quartzManager.runJobNow(quartzJob.getJobName(), quartzJob.getJobGroupName());
        return Result.ok(Boolean.TRUE);
    }

    /**
     * 检查是否存在
     *
     * @param sysQuartzJob quartz任务
     * @return boolean
     */
    private boolean checkExists(SysQuartzJob sysQuartzJob) {
        return Objects.isNull(this.getById(sysQuartzJob.getId()));
    }

}




