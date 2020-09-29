<template>
  <div class="allocateNewStudents">
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
          <el-table-column label="教师学院" prop="departmentName" align="center"></el-table-column>
          <el-table-column label="出生日期" align="center">
            <template slot-scope="scope">
              {{formatDate(scope.row.birth)}}
            </template>
          </el-table-column>
        </el-table>
      </div>
    </assets-page>
  </div>
</template>

<script>
  import { dataMixin } from '@/common/js/mixin'
  import routes from '@/common/js/config/routes'
  import AssetsPage from '@/views/project/task/components/assets-page-allocateTeachers'
  import { queryTeacher } from 'api/teacherAPI'
  import { allocateTeachersToClass } from 'api/classAPI'
  import { listMajorNames } from 'api/majorAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'
  import { listDepartmentNames } from 'api/departmentAPI'

  export default {
    name: 'traffic',
    mixins: [dataMixin],
    data () {
      return {
        searchForm: {
          name: '',
          majorId: '',
          classId: '',
          exceptClass: true,
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
        departmentNames: []
      }
    },
    created () {
      this.getDepartmentNames()
      this.getUnallocateStudents()
    },
    methods: {
      async getDepartmentNames () {
        const res = await listDepartmentNames()
        if (res.success) {
          this.departmentNames = res.data
        } else {
          this.getDepartmentNames()
        }
      },
      async getAllocateStudents () {
        this.searchForm.classId = this.$route.params.classId
        const res = await queryTeacher(this.searchForm)
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getAllocateStudents()
        }
      },
      async getUnallocateStudents () {
        this.searchForm.classId = this.$route.params.classId
        this.searchForm.exceptClass = true
        const res = await queryTeacher(this.searchForm)
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getUnallocateStudents()
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
      async getPageData () {
        const res = await queryTeacher(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getPageData()
        }
      },
      async deletePageData () {
        const res = await allocateTeachersToClass(this.multipleSelection, this.$route.params.classId)
        if (res.success) {
          this.$message.success('分配成功')
          this.getPageData()
        } else {
          this.deletePageData()
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
  .allocateNewStudents {
    padding: 20px;

    .student-icon {
      font-size: 28px;
    }
  }
</style>
