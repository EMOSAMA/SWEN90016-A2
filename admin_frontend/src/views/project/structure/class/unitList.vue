<template>
  <div class="unitList">
    <assets-page
      :total="totalNumbers"
      :search-form="searchForm"
      :currentPage="searchForm.pageIndex"
      @current-change="handleCurrentChange"
      @data-add="showAdd"
      @data-cancel="handleCancel"
      @data-delete="handleDelete"
      @data-search="handleSearch">
      <el-form-item label="课程名称" slot="search">
        <el-input v-model="searchForm.unitName" placeholder="课程名字"></el-input>
      </el-form-item>
      <el-form-item label="课程学年" slot="search">
        <el-input v-model="searchForm.year" placeholder="课程学年" @keydown.native="keydownhandle"></el-input>
      </el-form-item>
      <el-form-item label="课程学期" slot="search">
        <el-select v-model="searchForm.semester" clearable placeholder="请选择">
          <el-option :label="item.key" :value="item.key" v-for="(item, index) in semester"
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
          <el-table-column label="学年" prop="year" align="center"></el-table-column>
          <el-table-column label="学期" prop="semester" align="center"></el-table-column>
          <el-table-column label="课程名字" prop="unitName" align="center"></el-table-column>
          <el-table-column label="分配老师" width="200" align="center">
            <template slot-scope="scope">
              <router-link :to="'/project/structure/teacherList/' + scope.row.id " class="link">
                <el-button type="primary">老师列表</el-button>
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
          <el-form-item label="学年">
            <el-input placeholder="请输入学年" v-model="formData.year"></el-input>
          </el-form-item>
          <el-form-item label="学期">
            <el-input placeholder="请输入学期" v-model="formData.semester"></el-input>
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
  import AssetsPage from '@/views/project/task/components/assets-page-unitList'
  import { queryAllocateUnits, deleteAllocateUnits } from 'api/allocateUnitsAPI'
  import { dateFtt } from '@/common/js/utils'
  import { emptyToNull } from '@/common/js/utils/form'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          classId: '',
          unitName: '',
          year: null,
          semester: null,
          pageIndex: 1
        },
        formData: {
          username: '',
          password: '',
          name: '',
          majorId: '',
          birth: ''
        },
        formStudents: [],
        test: 'a',
        majorNames: [],
        semester:[
          {'key':'第一学期'},
          {'key': '第二学期'}
        ]
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      async getPageData () {
        this.searchForm.classId = this.$route.params.classId
        const res = await queryAllocateUnits(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
        } else {
          this.getAllocateUnits()
        }
      },
      formatDate (date) {
        return dateFtt('yyyy-MM-dd', new Date(date))
      },
      async deletePageData () {
        const res = await deleteAllocateUnits(this.multipleSelection)
        if (res.success) {
          this.$message.success('移除成功')
          this.getPageData()
        } else {
          this.deletePageData()
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
  .unitList {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
