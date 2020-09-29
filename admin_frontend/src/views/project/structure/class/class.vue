<template>
  <div class="class">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="班级名" slot="search">
        <el-input v-model="searchForm.className" placeholder="班级名称"></el-input>
      </el-form-item>
      <el-form-item label="班级专业" slot="search">
        <el-select v-model="searchForm.majorId" clearable placeholder="请选择专业">
          <el-option :label="item.majorName" :value="item.id" v-for="(item, index) in majorNames"
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
          <el-table-column label="班级ID" prop="id" align="center"></el-table-column>
          <el-table-column label="班级名" prop="className" align="center"></el-table-column>
          <el-table-column label="班级描述" prop="classDescription" align="center"></el-table-column>
          <el-table-column label="班级专业" align="center">
            <template slot-scope="scope">
              {{majorIdToName(scope.row.majorId)}}
            </template>
          </el-table-column>
          <el-table-column label="注册日期" align="center">
            <template slot-scope="scope">
              {{formatDate(scope.row.enrollmentDate)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row)">编辑</el-button>
            </template>
          </el-table-column>
          <el-table-column label="分配学生" width="200" align="center">
            <template slot-scope="scope">
              <router-link :to="'/project/structure/studentList/' + scope.row.id " class="link">
                <el-button type="primary">学生列表</el-button>
              </router-link>
            </template>
          </el-table-column>
          <el-table-column label="分配课程" width="200" align="center">
            <template slot-scope="scope">
              <router-link :to="'/project/structure/unitList/' + scope.row.id " class="link">
                <el-button type="primary">课程列表</el-button>
              </router-link>
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
          <el-form-item label="班级名">
            <el-input placeholder="请输入班级名" v-model="formData.className"></el-input>
          </el-form-item>
          <el-form-item label="班级描述">
            <el-input placeholder="请输入班级描述" v-model="formData.classDescription"></el-input>
          </el-form-item>
          <el-form-item label="班级专业">
            <el-select v-model="formData.majorId" clearable placeholder="请选择专业">
              <el-option :label="item.majorName" :value="item.id" v-for="(item, index) in majorNames"
                         :key="index"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="注册日期">
            <el-date-picker
              v-model="formData.enrollmentDate"
              type="datetime"
              placeholder="请选择注册日期"></el-date-picker>
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
  import { queryClass, addClass, updateClass, deleteClass } from 'api/classAPI'
  import { listMajorNames } from 'api/majorAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          className: '',
          majorId: '',
          pageIndex: 1
        },
        formData: {
          className: '',
          classDescription: '',
          majorId: '',
          enrollmentDate: '',
          fee: null,
          dormitoryFee: null,
          bookFee: null,
          totalFee: null
        },
        formStudents: [],
        majorNames: []
      }
    },
    created () {
      this.getMajorNames()
      this.getPageData()
    },
    methods: {
      majorIdToName (majorId) {
        for (let i = 0; i < this.majorNames.length; i++) {
          if (this.majorNames[i].id === majorId) {
            return this.majorNames[i].majorName
          }
        }
        return '无匹配专业'
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async getMajorNames () {
        const res = await listMajorNames()
        if (res.success) {
          this.majorNames = res.data
        } else {
          this.getMajorNames()
        }
      },
      showAdd () {
        this.dialogTitle = '班级新增'
        this.toggleDialog()
      },
      showEdit (data) {
        this.dialogTitle = '班级编辑'
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
          res = await updateClass(this.formData)
          message = '修改成功'
        } else {
          res = await addClass(this.formData)
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
        const res = await queryClass(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await deleteClass(this.multipleSelection)
        if (res.success) {
          this.$message.success('删除成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      _checkForm () {
        if (!this.formData.className) {
          this.$message.error('请填写班级名')
          return false
        }
        if (!this.formData.majorId) {
          this.$message.error('请填写班级所属专业')
          return false
        }
        if (!this.formData.enrollmentDate) {
          this.$message.error('请填写班级注册日期')
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
  .class {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
