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
      <el-form-item label="学生姓名" slot="search">
        <el-input v-model="searchForm.name" placeholder="学生名称"></el-input>
      </el-form-item>
      <el-form-item label="学生专业" slot="search">
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
          <el-table-column label="学生账号" prop="username" align="center"></el-table-column>
          <el-table-column label="学生姓名" prop="name" align="center"></el-table-column>
          <el-table-column label="学生专业" prop="majorName" align="center"></el-table-column>
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
  import AssetsPage from '@/views/project/task/components/assets-page-allocateStudents'
  import { queryStudent } from 'api/studentAPI'
  import { allocateStudentsToClass } from 'api/classAPI'
  import { listMajorNames } from 'api/majorAPI'
  import { emptyToNull } from '@/common/js/utils/form'
  import { dateFtt } from '@/common/js/utils'

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
        majorNames: []
      }
    },
    created () {
      this.getMajorNames()
      this.getUnallocateStudents()
    },
    methods: {
      async getAllocateStudents () {
        this.searchForm.classId = this.$route.params.classId
        const res = await queryStudent(this.searchForm)
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
        const res = await queryStudent(this.searchForm)
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
        const res = await queryStudent(emptyToNull(this.searchForm))
        if (res.success) {
          this.tableData = res.data.results
          this.totalNumbers = res.data.totalNumbers
          console.log(this.tableData)
        } else {
          this.getPageData()
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
