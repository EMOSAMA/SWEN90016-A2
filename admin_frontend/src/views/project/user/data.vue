<template>
  <div class="user-index">
    <div class="search-wrapper">
      <el-form v-model="searchForm" :inline="true">
        <el-form-item label="学生名字">
          <el-input v-model="searchForm.userName" placeholder="学生名称"></el-input>
        </el-form-item>
        <el-form-item label="学生专业">
          <el-input v-model="searchForm.major" placeholder="专业名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="operate-wrapper">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" @click="handleDelete(multipleSelection)">删除</el-button>
    </div>
    <div class="table-wrapper">
      <template>
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="序号" type="index" :index="indexMethod" width="100"></el-table-column>
          <el-table-column label="账号" prop="userName" width="180"></el-table-column>
          <el-table-column label="项目名称" prop="projectName" width="180"></el-table-column>
          <el-table-column label="功能">
            <template slot-scope="scope">
              <div class="auth-wrapper"
                   v-for="(item, index) in processFunctions(scope.row.functions)"
                   :key="index" :class="{pointer: item.children.length > 0}">
                <el-tooltip placement="top" v-if="item.children.length > 0">
                  <div slot="content">
                    <span style="display: inline-block; padding:0 10px;" v-for="(child, index) in processFunctions(item.children)"
                          :key="index"> {{ child.cnName }}</span>
                  </div>
                  <span class="active">{{ item.cnName }}</span>
                </el-tooltip>
                <span v-else>{{ item.cnName }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="150">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="handleEdit(scope.row.projectId)">编辑</el-button>
              <el-button type="danger" size="mini" @click="handleDelete([scope.row.projectId])">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
    </div>
    <div class="page-wrapper">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalNumbers"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import { getProjects, deleteProject } from 'api/project'
  import { emptyToNull } from '@/common/js/utils/form'

  export default {
    name: 'auth',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          userName: '',
          major: '',
          pageIndex: 1
        }
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      // 新增项目
      handleAdd () {
        this.$router.push('/project/user/add')
      },
      // 编辑项目
      handleEdit (id) {
        this.$router.push(`/project/user/edit/${id}`)
      },
      handleSelectionChange (val) {
        const selectData = val.map(item => item.projectId)
        this.multipleSelection = selectData
      },
      processFunctions (functions) {
        return functions.filter(item => item.checked)
      },
      async getPageData () {
        const projectId = this.$route.params.projectId
        const request = { projectId, ...this.searchForm }
        const res = await getProjects(emptyToNull(request))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        }
      },
      async deletePageData () {
        const res = await deleteProject(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        }
      }
    }
  }
</script>

<style lang="scss" scoped>
  .user-index {
    position: relative;
    padding: 20px;

    .table-wrapper {
      margin-top: 20px;

      .auth-wrapper {
        display: inline-block;
        margin-right: 20px;

        &:last-child {
          margin-right: 0;
        }

        .pointer {
          cursor: pointer;
        }

        .active {
          color: #409eff;
        }

        .child-auth {
          display: inline-block;
          padding: 10px 10px;
        }
      }
    }

    .page-wrapper {
      margin-top: 50px;
      text-align: center;
    }
  }
</style>
