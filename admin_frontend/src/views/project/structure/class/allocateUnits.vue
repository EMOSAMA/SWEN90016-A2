<template>
  <div class="allocateUnits">
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
        <el-input v-model="searchForm.unitName" placeholder="课程名称"></el-input>
      </el-form-item>
      <el-form-item label="课程类型" slot="search">
        <el-select v-model="searchForm.unitType" clearable placeholder="请选择类型">
          <el-option v-for="item in unitTypes"
                     :label="item.type"
                     :key="item.type"
                     :value="item.type"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <div slot="table">
        <el-table
          ref="multipleTable"
          :data="tableData"
          style="width: 100%"
          :border=true
          @selection-change="handleSelectionChange">
          <el-table-column label="序号" type="index" :index="indexMethod" width="55" align="center"></el-table-column>
          <el-table-column label="课程名称" prop="unitName" align="center"></el-table-column>
          <el-table-column label="课程描述" prop="unitDescription" align="center"></el-table-column>
          <el-table-column label="课程类型" prop="unitType" align="center"></el-table-column>
          <el-table-column label="操作" width="250" align="center">
            <template slot-scope="scope">
              <el-button type="primary" @click="showEdit(scope.row.id)">选课</el-button>
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
            <el-input v-model="formData.year" placeholder="请输入学年" @keydown.native="keydownhandle"></el-input>
          </el-form-item>
          <el-form-item label="学期">
            <el-select v-model="formData.semester" clearable placeholder="请选择学期">
              <el-option :label="item.key" :value="item.key" v-for="(item, index) in semesterList"
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
  import AssetsPage from '@/views/project/task/components/assets-page-allocateUnits'
  import { queryUnit } from 'api/unitAPI'
  import { createAllocateUnits } from 'api/allocateUnitsAPI'
  import { listMajorNames } from 'api/majorAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          unitName: '',
          unitType: '',
          pageIndex: 1
        },
        formData: {
          classId: '',
          unitId: '',
          year: null,
          semester: null
        },
        formStudents: [],
        test: 'a',
        majorNames: [],
        unitTypes:[
          { type: "专业课程"},
          { type: "公共课程"},
          { type: "线上课程"}
        ],
        semesterList:[{'key':'第一学期'},{'key':'第二学期'}]
      }
    },
    created () {
      this.getPageData()
    },
    methods: {
      keydownhandle (event) {
        const { key } = event
        // 或者根据键码判断, 下面过滤掉除数字和退格外的所有输入
        if (new RegExp(/[0-9]/).test(key) || key === 'Backspace') return;
        event.preventDefault()
      },
      async getPageData () {
        this.searchForm.classId = this.$route.params.classId
        const res = await queryUnit(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getAllocateStudents()
        }
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
      async deletePageData () {
        const res = await allocateStudentsToClass(this.multipleSelection, this.$route.params.classId)
        if (res.success) {
          this.$message.success('分配成功')
          this.getPageData()
        } else {
          this.deletePageData()
        }
      },
      showAdd () {
        this.dialogTitle = '班级新增'
        this.toggleDialog()
      },
      showEdit (id) {
        this.dialogTitle = '班级编辑'
        this.formData.unitId = id
        this.toggleDialog()
      },
      async save () {
        if (!this._checkForm()) {
          return
        }
        let message = ''
        let res = null
        this.formData.classId = this.$route.params.classId
        res = await createAllocateUnits(emptyToNull(this.formData))
        message = '选课成功'
        if (res.success) {
          this.$message.success(message)
          this.getPageData()
          this.toggleDialog()
        } else {
          this.save()
        }
      },
      _checkForm () {
        if (!this.formData.year) {
          this.$message.error('请填写学年')
          return false
        }
        if (!this.formData.semester) {
          this.$message.error('请填写学期')
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
  .allocateUnits {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
