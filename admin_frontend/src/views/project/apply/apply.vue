<template>
  <div class="apply">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="项目类别" slot="search">
        <el-select v-model="searchForm.projectType" clearable placeholder="请选择项目">
          <el-option :label="item.type" :value="item.type" v-for="(item, index) in projectTypes"
                     :key="index"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="学生姓名" slot="search">
        <el-input v-model="searchForm.studentName" placeholder="学生姓名"></el-input>
      </el-form-item>
      <el-form-item label="处理进度" slot="search">
        <el-select v-model="searchForm.status" clearable placeholder="请选择进度">
          <el-option :label="item.type" :value="item.type" v-for="(item, index) in processTypes"
                     :key="index"></el-option>
        </el-select>
      </el-form-item>
      <div slot="table">
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"></el-table-column>
          <el-table-column label="序号" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
          <el-table-column label="项目类型" prop="projectType" align="center"></el-table-column>
          <el-table-column label="学生姓名" prop="studentName" align="center"></el-table-column>
          <el-table-column label="申请理由" prop="applyReason" align="center"></el-table-column>
          <el-table-column label="处理进度" prop="status" align="center"></el-table-column>
          <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">处理</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </assets-page>
    <div class="dialog-wrapper">
      <el-dialog
        :title="dialogTitle"
        :visible.sync="showDialog"
        :close-on-click-modal=false
        width="600px"
        @closed="dialogClosed">
        <el-form :model="formData" label-width="100px">
          <el-form-item label="处理进度">
            <el-select v-model="formData.status" clearable placeholder="请选择进度">
              <el-option :label="item.type" :value="item.type" v-for="(item, index) in processTypes"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="save">保存</el-button>
            <el-button @click="toggleDialog">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/views/project/task/components/assets-page-apply'
  import { queryApplyProject, addApplyProject, updateApplyProject, deleteApplyProject } from 'api/applyProjectAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'
  import $store from '../../../store/state'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          studentId: null,
          studentName: null,
          projectType: '',
          status: '',
          pageIndex: 1
        },
        formData: {
          studentId: $store.userId,
          projectType: '',
          applyReason: '',
          status: null,
          studentName: ''
        },
        projectTypes:[{'type':'奖学金'},{'type':'贫困生'}],
        processTypes:[{'type':'未处理'},{'type':'处理中'},{'type':'接受申请'},{'type':'拒绝申请'}]
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      showAdd () {
        this.dialogTitle = '申请项目新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '申请项目编辑'
        this.setFormData(data)
        this.toggleDialog()
      },
      async save () {
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        if (this.formData.id) {
          res = await updateApplyProject(this.formData)
          message = '修改成功'
        } else {
          this.formData.studentId=$store.userId
          res = await addApplyProject(this.formData)
          message = '新增成功'
        }
        if (res.success) {
          this.$message.success(message)
          this.getPageData()
          this.toggleDialog()
        } else {
          this.save()
        }
      },
      async getPageData () {
        const res = await queryApplyProject(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteApplyProject(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.projectType) {
          this.$message.error('请选择项目类型')
          return false
        }
        if (!this.formData.applyReason) {
          this.$message.error('请填写原因')
          return false
        }
        return true
      }
    },
    components: {
      AssetsPage
    },
    computed: {
      routes () {
        return routes.student
      }
    }
  }
</script>

<style lang="scss" scoped>
  .apply {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
