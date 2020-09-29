<template>
  <div class="teacher">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="教师姓名" slot="search">
        <el-input v-model="searchForm.name" placeholder="教师名称"></el-input>
      </el-form-item>
      <el-form-item label="所属学院" slot="search">
        <el-select v-model="searchForm.departmentId" clearable placeholder="请选择学院">
          <el-option :label="item.departmentName" :value="item.id" v-for="(item, index) in departmentNames"
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
          <el-table-column label="教师账号" prop="username" align="center"></el-table-column>
          <el-table-column label="教师姓名" prop="name" align="center"></el-table-column>
          <el-table-column label="所属学院" prop="departmentName" align="center"></el-table-column>
          <el-table-column label="年龄" prop="age" align="center"></el-table-column>
          <el-table-column label="民族" prop="nationality" align="center"></el-table-column>
          <el-table-column label="政治面貌" prop="politicalStatus" align="center"></el-table-column>
          <el-table-column label="身份证号码" prop="identityNumber" align="center"></el-table-column>
          <el-table-column label="出生日期" align="center">
            <template slot-scope="scope">
              {{formatDate(scope.row.birth)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">编辑</el-button>
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
          <el-form-item label="教师账号">
            <el-input placeholder="请输入教师账号" v-model="formData.username"></el-input>
          </el-form-item>
          <el-form-item label="账号密码">
            <el-input placeholder="请输入教师账号密码" v-model="formData.password"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名">
            <el-input placeholder="请输入教师姓名" v-model="formData.name"></el-input>
          </el-form-item>
          <el-form-item label="学院">
            <el-select v-model="formData.departmentId" clearable placeholder="请选择所属学院">
              <el-option :label="item.departmentName" :value="item.id" v-for="(item, index) in departmentNames"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input placeholder="请输入年龄" v-model="formData.age"></el-input>
          </el-form-item>
          <el-form-item label="民族">
            <el-input placeholder="请输入民族" v-model="formData.nationality"></el-input>
          </el-form-item>
          <el-form-item label="政治面貌">
            <el-input placeholder="请输入政治面貌" v-model="formData.politicalStatus"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码">
            <el-input placeholder="请输入身份证号码" v-model="formData.identityNumber"></el-input>
          </el-form-item>
          <el-form-item label="出生日期">
            <el-date-picker
              v-model="formData.birth"
              type="datetime"
              placeholder="请选择出生日期"></el-date-picker>
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
  import AssetsPage from '@/views/project/task/components/assets-page'
  import { queryTeacher, addTeacher, updateTeacher, deleteTeacher } from 'api/teacherAPI'
  import { listDepartmentNames } from 'api/departmentAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          name: '',
          departmentId: '',
          pageIndex: 1
        },
        formData: {
          username: '',
          password: '',
          name: '',
          departmentId: '',
          birth: '',
          age: '',
          nationality: '',
          politicalStatus: '',
          identityNumber: ''
        },
        departmentNames: []
      }
    },
    created () {
      this.getDepartmentNames()
      this.getPageData()
    },
    methods: {
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getDepartmentNames () {
        const res = await listDepartmentNames()
        if (res.success) {
          this.departmentNames = res.data
        } else {
          this.getDepartmentNames()
        }
      },
      showAdd () {
        this.dialogTitle = '教师账号新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '教师账号编辑'
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
          res = await updateTeacher(this.formData)
          message = '修改成功'
        } else {
          res = await addTeacher(this.formData)
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
        const res = await queryTeacher(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteTeacher(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.username) {
          this.$message.error('请填写教师用户名')
          return false
        }
        if (!this.formData.password) {
          this.$message.error('请填写密码')
          return false
        }
        if (!this.formData.name) {
          this.$message.error('请填写教师姓名')
          return false
        }
        if (!this.formData.departmentId) {
          this.$message.error('请填写教师所属学院')
          return false
        }
        if (!this.formData.birth) {
          this.$message.error('请填写教师出生日期')
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
  .teacher {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
